package com.chompfooddeliveryapp.service.serviceInterfaces;

import com.chompfooddeliveryapp.model.meals.FavoriteMeal;

import java.util.List;

public interface FavoriteMealService {
    FavoriteMeal createFavoriteMeal(FavoriteMeal favoriteMeal);

    void deleteFavoriteMeal(Long id);

    public List<FavoriteMeal> getAllFavoriteMealsByAUser(Long userId);


}
