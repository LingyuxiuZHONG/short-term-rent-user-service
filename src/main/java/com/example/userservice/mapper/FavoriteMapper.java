package com.example.userservice.mapper;

import com.example.userservice.dto.FavoriteUpdateDTO;
import com.example.userservice.model.Favorite;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface FavoriteMapper {
    void addFavorite(FavoriteUpdateDTO favoriteUpdateDTO);

    void removeFavorite(FavoriteUpdateDTO favoriteUpdateDTO);// 从爱心处删除

    List<Favorite> getFavoritesByUserId(Long userId);

    void deleteFavorite(Long favoriteId);// 从收藏页删除
}
