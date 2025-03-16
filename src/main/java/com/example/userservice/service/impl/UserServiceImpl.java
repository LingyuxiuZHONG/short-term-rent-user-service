package com.example.userservice.service.impl;

import ch.qos.logback.core.util.StringUtil;
import com.example.common.exception.BusinessException;
import com.example.feignapi.vo.UserVO;
import com.example.userservice.dto.UserLoginDTO;
import com.example.userservice.dto.UserRegisterDTO;
import com.example.userservice.dto.UserUpdateDTO;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.model.User;
import com.example.userservice.service.UserService;
import com.example.userservice.utils.JwtTokenUtil;
import com.example.userservice.utils.UserValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public UserVO register(UserRegisterDTO userRegisterDTO) {
        log.info("用户注册开始，参数：{}", userRegisterDTO);

        UserValidationUtils.validateUserRegister(userRegisterDTO, userMapper);

        User user = new User();
        BeanUtils.copyProperties(userRegisterDTO, user);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        userMapper.insert(user);

        String token = jwtTokenUtil.generateToken(user.getUsername());
        log.info("用户注册成功，用户名：{}", user.getUsername());

        UserVO userVO = convertToUserVO(user);
        userVO.setToken(token);

        return userVO;
    }

    @Override
    public UserVO login(UserLoginDTO userLoginDTO) {
        log.info("用户登录开始，用户名：{}", userLoginDTO.getUsername());

        User user = userMapper.getByUsername(userLoginDTO.getUsername());
        if (user == null || !passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            log.warn("用户登录失败，用户名或密码错误，用户名：{}", userLoginDTO.getUsername());
            throw new BusinessException("用户名或密码错误");
        }

        String token = jwtTokenUtil.generateToken(user.getUsername());
        log.info("用户登录成功，用户名：{}", user.getUsername());

        UserVO userVO = convertToUserVO(user);
        userVO.setToken(token);

        return userVO;
    }

    @Override
    public UserVO getUserById(Long id) {
        log.info("查询用户信息，用户ID：{}", id);

        User user = userMapper.getById(id);
        if (user == null) {
            log.warn("查询用户信息失败，用户不存在，用户ID：{}", id);
            throw new BusinessException("用户不存在");
        }

        log.info("查询用户信息成功，用户ID：{}", id);
        return convertToUserVO(user);
    }

    @Override
    public UserVO updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        log.info("更新用户信息开始，用户ID：{}，参数：{}", id, userUpdateDTO);

        UserValidationUtils.validateUserUpdate(userUpdateDTO, userMapper);

        User user = userMapper.getById(id);
        if (user == null) {
            log.warn("更新用户信息失败，用户不存在，用户ID：{}", id);
            throw new BusinessException("用户不存在");
        }

        if (!StringUtil.isNullOrEmpty(userUpdateDTO.getPassword())) {
            user.setPassword(passwordEncoder.encode(userUpdateDTO.getPassword()));
        }

        BeanUtils.copyProperties(userUpdateDTO, user);
        userMapper.update(user);

        log.info("更新用户信息成功，用户ID：{}", id);
        return convertToUserVO(user);
    }

    @Override
    public void deleteUser(Long id) {
        log.info("删除用户开始，用户ID：{}", id);

        int deletedRows = userMapper.delete(id);
        if (deletedRows == 0) {
            log.warn("删除用户失败，用户ID：{}", id);
            throw new BusinessException("删除用户失败");
        }

        log.info("删除用户成功，用户ID：{}", id);
    }

    private UserVO convertToUserVO(User user) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
}
