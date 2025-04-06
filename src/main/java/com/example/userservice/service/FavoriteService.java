package com.example.userservice.service;

import com.example.feignapi.vo.FavoriteCard;
import com.example.userservice.dto.FavoriteUpdateDTO;

import java.util.List;
import java.util.Set;

public interface FavoriteService {

    void updateFavorite(FavoriteUpdateDTO favoriteUpdateDTO);

    List<FavoriteCard> getFavorites(Long userId);

    void deleteFavorite(Long id);

    Set<Long> getFavoriteListingIds(Long userId);
}
