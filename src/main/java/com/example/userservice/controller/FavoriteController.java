package com.example.userservice.controller;


import com.example.common.ApiResponse;
import com.example.feignapi.vo.FavoriteCard;
import com.example.userservice.dto.FavoriteUpdateDTO;
import com.example.userservice.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PutMapping("/favorites")
    public ResponseEntity<ApiResponse<String>> updateFavorite(@RequestBody FavoriteUpdateDTO favoriteUpdateDTO) {
        favoriteService.updateFavorite(favoriteUpdateDTO);
        return ResponseEntity.ok(ApiResponse.success("更新成功"));
    }

    @GetMapping("/{userId}/favorites")
    public ResponseEntity<ApiResponse<List<FavoriteCard>>> getFavorites(@PathVariable Long userId){
        List<FavoriteCard> list = favoriteService.getFavorites(userId);
        return ResponseEntity.ok(ApiResponse.success("查找成功", list));
    }

    @DeleteMapping("/{userId}/favorites/{id}")
    public ResponseEntity<ApiResponse<String>> deleteFavorite(@PathVariable Long userId,@PathVariable Long id){
        favoriteService.deleteFavorite(id);
        return ResponseEntity.ok(ApiResponse.success("删除成功"));
    }

    @PostMapping("/{userId}/favoritesSet")
    ResponseEntity<ApiResponse<Set<Long>>> getFavoriteListingIds(@PathVariable Long userId){
        Set<Long> set = favoriteService.getFavoriteListingIds(userId);
        return ResponseEntity.ok(ApiResponse.success("查询成功", set));
    }
}
