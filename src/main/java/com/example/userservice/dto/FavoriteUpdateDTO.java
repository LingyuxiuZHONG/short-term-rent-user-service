package com.example.userservice.dto;


import lombok.Data;

@Data
public class FavoriteUpdateDTO {
    private Long userId;
    private Long listingId;
    private Boolean isFavorite;
}
