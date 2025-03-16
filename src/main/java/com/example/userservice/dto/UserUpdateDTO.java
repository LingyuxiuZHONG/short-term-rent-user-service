package com.example.userservice.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String profileImage;
    private String description;
}
