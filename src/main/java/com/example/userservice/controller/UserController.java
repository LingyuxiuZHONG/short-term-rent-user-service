package com.example.userservice.controller;


import com.example.common.ApiResponse;
import com.example.feignapi.vo.UserVO;
import com.example.userservice.dto.*;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 用户注册
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserVO>> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        UserVO userVO = userService.register(userRegisterDTO);
        return ResponseEntity.ok(ApiResponse.success("用户注册成功", userVO));
    }

    // 用户登录
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserVO>> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        UserVO userVO = userService.login(userLoginDTO);
        return ResponseEntity.ok(ApiResponse.success("用户登录成功", userVO));
    }

    // 获取用户信息
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserVO>> getUser(@PathVariable Long id) {
        UserVO userVO = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success("获取用户信息成功", userVO));
    }

    // 更新用户信息
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserVO>> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        UserVO userVO = userService.updateUser(id,userUpdateDTO);
        return ResponseEntity.ok(ApiResponse.success("更新用户信息成功", userVO));
    }

    @PostMapping("/{id}/verify-password")
    public ResponseEntity<ApiResponse<String>> verifyPassword(@PathVariable Long id, @RequestBody PasswordVerifyDTO passwordVerifyDTO){
        userService.verifyPassword(id,passwordVerifyDTO);
        return ResponseEntity.ok(ApiResponse.success("验证成功"));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<ApiResponse<String>> updatePassword(@PathVariable Long id, @RequestBody PasswordUpdateDTO passwordUpdateDTO){
        userService.updatePassword(id,passwordUpdateDTO);
        return ResponseEntity.ok(ApiResponse.success("更新密码成功"));
    }

    @PostMapping("/{id}/avatar")
    public ResponseEntity<ApiResponse<String>> uploadAvatar(@PathVariable Long id, @RequestParam("avatar") MultipartFile avatar){
        String url = userService.uploadAvatar(id,avatar);
        return ResponseEntity.ok(ApiResponse.success("上传成功", url));
    }

    // 删除用户
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success("删除成功"));
    }



}
