package com.example.userservice.service;

import com.example.feignapi.vo.UserVO;
import com.example.userservice.dto.*;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    UserVO register(UserRegisterDTO userRegisterDTO);
    UserVO login(UserLoginDTO userLoginDTO);
    UserVO getUserById(Long id);
    UserVO updateUser(Long id,UserUpdateDTO userUpdateDTO);
    void deleteUser(Long id);

    String uploadAvatar(Long id, MultipartFile avatar);

    void verifyPassword(Long id, PasswordVerifyDTO passwordVerifyDTO);

    void updatePassword(Long id, PasswordUpdateDTO passwordUpdateDTO);
}
