package com.example.userservice.dto;


import lombok.Data;

@Data
public class PasswordUpdateDTO {
    private String oldPassword;
    private String password;
}
