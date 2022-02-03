package com.chompfooddeliveryapp.service.serviceInterfaces;

import com.chompfooddeliveryapp.model.meals.FavoriteMeal;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FavoriteMealService {
//    FavoriteMeal createFavoriteMeal(Long userId, Long menuId);
    FavoriteMeal createFavoriteMeal(Long userId, Long menuId);

    String removeFromFavoriteMeal(Long userId, Long menuId);

    public List<FavoriteMeal> getAllFavoriteMealsByAUser(Long userId);

    public FavoriteMeal getAParticularFavoriteMeal(Long userId, Long menuId);

}
