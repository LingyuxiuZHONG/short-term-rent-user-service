package com.example.userservice.utils;

import ch.qos.logback.core.util.StringUtil;
import com.example.common.exception.BusinessException;
import com.example.userservice.dto.UserRegisterDTO;
import com.example.userservice.dto.UserUpdateDTO;
import com.example.userservice.mapper.UserMapper;

public class UserValidationUtils {

    public static void validateUserRegister(UserRegisterDTO userRegisterDTO, UserMapper userMapper) {
        validateEmail(userRegisterDTO.getEmail(), userMapper);
//        validatePassword(userRegisterDTO.getPassword());
    }

    public static void validateUserUpdate(UserUpdateDTO userUpdateDTO, UserMapper userMapper) {

        if(!StringUtil.isNullOrEmpty(userUpdateDTO.getPhoneNumber())){
            validatePhoneNumber(userUpdateDTO.getPhoneNumber(), userMapper);
        }
        if(!StringUtil.isNullOrEmpty(userUpdateDTO.getEmail())){
            validateEmail(userUpdateDTO.getEmail(), userMapper);
        }

    }



    // 手机号验证
    private static void validatePhoneNumber(String phoneNumber, UserMapper userMapper) {
//        if (!phoneNumber.matches("^1[3-9]\\d{9}$")) {
//            throw new BusinessException("手机号格式不正确");
//        }
        if (userMapper.getByPhoneNumber(phoneNumber) != null) {
            throw new BusinessException("手机号已存在");
        }
    }

    // 邮箱验证
    private static void validateEmail(String email, UserMapper userMapper) {
        if (userMapper.getByEmail(email) != null) {
            throw new BusinessException("邮箱已存在");
        }
    }

    // 密码验证
    private static void validatePassword(String password) {
        if (password.length() < 8) {
            throw new BusinessException("密码长度不足");
        }
        if (!password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || !password.matches(".*[0-9].*")) {
            throw new BusinessException("密码强度不足，需包含大小写字母和数字");
        }
    }


}


