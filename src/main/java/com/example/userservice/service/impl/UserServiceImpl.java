package com.example.userservice.service.impl;

import com.example.common.exception.BusinessException;
import com.example.feignapi.clients.ListingClient;
import com.example.feignapi.vo.UserCard;
import com.example.feignapi.vo.UserVO;
import com.example.userservice.dto.*;
import com.example.userservice.mapper.FavoriteMapper;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.model.User;
import com.example.userservice.service.UserService;
import com.example.userservice.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final FavoriteMapper favoriteMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final ListingClient listingClient;


    @Override
    public UserVO register(UserRegisterDTO userRegisterDTO) {
        log.info("用户注册开始，参数：{}", userRegisterDTO);

//        UserValidationUtils.validateUserRegister(userRegisterDTO, userMapper);

        User user = new User();
        BeanUtils.copyProperties(userRegisterDTO, user);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        userMapper.register(user);

        String token = jwtTokenUtil.generateToken(user.getEmail());
        log.info("用户注册成功");

        UserVO userVO = convertToUserVO(user);
        userVO.setToken(token);

        return userVO;
    }

    @Override
    public UserVO login(UserLoginDTO userLoginDTO) {
        log.info("用户登录开始，邮箱：{}", userLoginDTO.getEmail());

        User user = userMapper.getByEmail(userLoginDTO.getEmail());
        if (user == null || !passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            log.warn("用户登录失败，邮箱或密码错误，邮箱：{}", userLoginDTO.getEmail());
            throw new BusinessException("邮箱或密码错误");
        }

        String token = jwtTokenUtil.generateToken(user.getEmail());
        log.info("用户登录成功");

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

//        UserValidationUtils.validateUserUpdate(userUpdateDTO, userMapper);

        User user = userMapper.getById(id);
        if (user == null) {
            log.warn("更新用户信息失败，用户不存在，用户ID：{}", id);
            throw new BusinessException("用户不存在");
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


    @Override
    public void uploadAvatar(Long id, String fileUrl) {
        User user = userMapper.getById(id);
        if(user == null){
            throw new BusinessException("用户不存在");
        }


        user.setAvatar(fileUrl);
        userMapper.uploadAvatar(user);

    }

    @Override
    public void verifyPassword(Long id, PasswordVerifyDTO passwordVerifyDTO) {
        User user = userMapper.getById(id);
        if(user == null || !passwordEncoder.matches(passwordVerifyDTO.getPassword(), user.getPassword())){
            throw new BusinessException("密码验证失败");
        }
    }

    @Override
    public void updatePassword(Long id, PasswordUpdateDTO passwordUpdateDTO) {
        User user = userMapper.getById(id);
        if(user == null || !passwordEncoder.matches(passwordUpdateDTO.getOldPassword(), user.getPassword())){
            throw new BusinessException("密码验证失败");
        }

        user.setPassword(passwordEncoder.encode(passwordUpdateDTO.getPassword()));
        userMapper.updatePassword(user);
    }

    @Override
    public UserCard getOtherUser(Long id) {

        User user = userMapper.getById(id);
        return convertToUserCard(user);
    }

    @Override
    public void resetPassword(ResetPasswordDTO resetPasswordDTO) {
        User user = userMapper.getByEmail(resetPasswordDTO.getEmail());
        if(user == null){
            throw new BusinessException("用户不存在失败");
        }

        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getPassword()));
        userMapper.updatePassword(user);
    }


    private UserVO convertToUserVO(User user) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    private UserCard convertToUserCard(User user){
        UserCard userCard = new UserCard();
        BeanUtils.copyProperties(user, userCard);
        return userCard;
    }
}
