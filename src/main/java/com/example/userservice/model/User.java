package com.example.userservice.model;



import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private Long roleId;
    private String profileImage;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private short isActive;
}
