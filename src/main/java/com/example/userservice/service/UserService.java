package com.example.userservice.service;

import com.example.feignapi.vo.UserVO;
import com.example.userservice.dto.*;

public interface UserService {
    UserVO register(UserRegisterDTO userRegisterDTO);
    UserVO login(UserLoginDTO userLoginDTO);
    UserVO getUserById(Long id);
    UserVO updateUser(Long id,UserUpdateDTO userUpdateDTO);
    void deleteUser(Long id);

    void uploadAvatar(Long id, String fileUrl);

    void verifyPassword(Long id, PasswordVerifyDTO passwordVerifyDTO);

    void updatePassword(Long id, PasswordUpdateDTO passwordUpdateDTO);


}
