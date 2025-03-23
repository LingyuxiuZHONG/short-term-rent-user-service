package com.example.userservice.service;

import com.example.feignapi.vo.FavoriteCard;
import com.example.userservice.dto.FavoriteUpdateDTO;

import java.util.List;

public interface FavoriteService {

    void updateFavorite(FavoriteUpdateDTO favoriteUpdateDTO);

    List<FavoriteCard> getFavorites(Long userId);

    void deleteFavorite(Long id);
}
