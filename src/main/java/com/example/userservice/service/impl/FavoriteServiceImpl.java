package com.example.userservice.service.impl;

import com.example.feignapi.clients.ListingClient;
import com.example.feignapi.vo.FavoriteCard;
import com.example.feignapi.vo.FavoriteListing;
import com.example.userservice.dto.FavoriteUpdateDTO;
import com.example.userservice.mapper.FavoriteMapper;
import com.example.userservice.model.Favorite;
import com.example.userservice.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

        List<FavoriteListing> listingInfos = listingClient.getFavoriteListings(listingIds).getBody().getData();

        List<FavoriteCard> favoriteCards = IntStream.range(0, listingInfos.size())
                .mapToObj(i -> new FavoriteCard(favorites.get(i).getId(), listingInfos.get(i), favorites.get(i).getCreatedAt()))
                .collect(Collectors.toList());


        return favoriteCards;


    }

    @Override
    public void deleteFavorite(Long id) {
        favoriteMapper.deleteFavorite(id);
    }
}
