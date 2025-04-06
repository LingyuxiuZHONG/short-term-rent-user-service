package com.example.userservice.service.impl;

import com.example.feignapi.clients.ListingClient;
import com.example.feignapi.vo.FavoriteCard;
import com.example.feignapi.vo.FavoriteListing;
import com.example.userservice.dto.FavoriteUpdateDTO;
import com.example.userservice.mapper.FavoriteMapper;
import com.example.userservice.model.Favorite;
import com.example.userservice.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteMapper favoriteMapper;

    private final ListingClient listingClient;


    @Override
    public void updateFavorite(FavoriteUpdateDTO favoriteUpdateDTO) {
        if(favoriteUpdateDTO.getIsFavorite()){
            favoriteMapper.addFavorite(favoriteUpdateDTO);
        }else{
            favoriteMapper.removeFavorite(favoriteUpdateDTO);
        }

    }

    @Override
    public List<FavoriteCard> getFavorites(Long userId) {
        List<Favorite> favorites = favoriteMapper.getFavoritesByUserId(userId);

        List<Long> listingIds = favorites.stream()
                .map(Favorite::getListingId)
                .collect(Collectors.toList());

        // 让 listingClient 直接返回一个 Map，避免手动遍历匹配
        if(listingIds.isEmpty()){
            return new ArrayList<>();
        }
        Map<Long, FavoriteListing> listingMap = listingClient.getFavoriteListings(listingIds)
                .getBody().getData()
                .stream()
                .collect(Collectors.toMap(FavoriteListing::getListingId, listing -> listing));

        // 直接用 stream.map() 进行数据合并
        return favorites.stream()
                .map(fav -> {
                    FavoriteListing listing = listingMap.get(fav.getListingId());
                    return new FavoriteCard(
                            fav.getId(),
                            fav.getListingId(),
                            listing != null ? listing.getTitle() : null,
                            listing != null ? listing.getAddress() : null,
                            listing != null ? listing.getImages() : null,
                            listing != null ? listing.getPrice() : null,
                            listing != null ? listing.getRating() : null,
                            fav.getCreatedAt()
                    );
                })
                .collect(Collectors.toList());
    }


    @Override
    public void deleteFavorite(Long id) {
        favoriteMapper.deleteFavorite(id);
    }

    @Override
    public Set<Long> getFavoriteListingIds(Long userId) {

        List<Favorite> favorites = favoriteMapper.getFavoritesByUserId(userId);
        Set<Long> favoriteListingIds = favorites.stream()
                .map(Favorite::getListingId) // 提取 listingId
                .collect(Collectors.toSet()); // 转换为 Set
        return favoriteListingIds;

    }
}
