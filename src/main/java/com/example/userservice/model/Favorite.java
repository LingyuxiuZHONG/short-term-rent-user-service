package com.example.userservice.model;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Favorite{
    private Long id;
    private Long userId;
    private Long listingId;
    private LocalDateTime createdAt;
}
