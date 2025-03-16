package com.example.userservice.service;

import com.example.feignapi.vo.UserVO;
import com.example.userservice.dto.UserLoginDTO;
import com.example.userservice.dto.UserRegisterDTO;
import com.example.userservice.dto.UserUpdateDTO;

public interface UserService {
    UserVO register(UserRegisterDTO userRegisterDTO);
    UserVO login(UserLoginDTO userLoginDTO);
    UserVO getUserById(Long id);
    UserVO updateUser(Long id,UserUpdateDTO userUpdateDTO);
    void deleteUser(Long id);
}
