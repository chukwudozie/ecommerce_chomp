package com.chompfooddeliveryapp.service.serviceInterfaces;

import com.chompfooddeliveryapp.model.meals.FavoriteMeal;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface FavoriteMealService {
    FavoriteMeal createFavoriteMeal(FavoriteMeal favoriteMeal);

    String removeFromFavoriteMeal(Long userId, Long menuId);

    public List<FavoriteMeal> getAllFavoriteMealsByAUser(Long userId);

    public FavoriteMeal getAParticularFavoriteMeal(Long userId, Long mealId);

}
