package com.chompfooddeliveryapp.service.serviceInterfaces;

import com.chompfooddeliveryapp.model.meals.FavoriteMeal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FavoriteMealService {
    FavoriteMeal createFavoriteMeal(FavoriteMeal favoriteMeal);

    String deleteFavoriteMeal(Long mealId);

    public List<FavoriteMeal> getAllFavoriteMealsByAUser(Long userId);


}
