package com.example.userservice.model;



import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phoneNumber;
    private Integer roleType;
    private String avatar;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private short isActive;
}
