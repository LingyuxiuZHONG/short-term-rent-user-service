package com.example.userservice.mapper;

import com.example.userservice.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User getById(Long id);
    User getByUsername(String username);
    User getByPhoneNumber(String phoneNumber);
    User getByEmail(String email);
    int insert(User user);
    int update(User user);
    int delete(Long id);
}
