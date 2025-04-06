package com.example.userservice.dto;


import lombok.Data;

@Data
public class ResetPasswordDTO {
    private String email;
    private String password;
}
