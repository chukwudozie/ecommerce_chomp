package com.chompfooddeliveryapp.service.serviceInterfaces;

import com.chompfooddeliveryapp.model.meals.FavoriteMeal;
import com.chompfooddeliveryapp.model.meals.MenuItem;
import com.chompfooddeliveryapp.model.users.User;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FavoriteMealService {

    MenuItem createFavoriteMeal(Long userId, Long menuId);

    String removeFromFavoriteMeal(Long userId, Long menuId);

    public List<FavoriteMeal> getAllFavoriteMealsByAUser(Long userId);

    public MenuItem getAParticularFavoriteMeal(Long userId, Long menuId);

}
