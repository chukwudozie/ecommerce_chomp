package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.exception.FavoriteNotFoundException;
import com.chompfooddeliveryapp.model.enums.MenuCategory;
import com.chompfooddeliveryapp.model.meals.FavoriteMeal;
import com.chompfooddeliveryapp.model.meals.MenuItem;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.repository.FavoriteMealRepository;
import com.chompfooddeliveryapp.repository.MenuItemRepository;
import com.chompfooddeliveryapp.repository.UserRepository;
import com.chompfooddeliveryapp.service.serviceInterfaces.FavoriteMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteMenuServiceImpl implements FavoriteMealService {

    private final FavoriteMealRepository favoriteMealRepository;



    @Autowired
    public FavoriteMenuServiceImpl(FavoriteMealRepository favoriteMealRepository, MenuItemRepository menuItemRepository, UserRepository userRepository) {
        this.favoriteMealRepository = favoriteMealRepository;
    }

    @Override
    public FavoriteMeal createFavoriteMeal(Long userId, Long menuId) {

        FavoriteMeal newfavoriteMeal;
        FavoriteMeal favoriteMeal = favoriteMealRepository.findFavoriteMealByUser_idAndMenu_id(userId, menuId)
                .orElse(newfavoriteMeal = new FavoriteMeal(userId, menuId));
        return favoriteMealRepository.save(newfavoriteMeal);
    }

    @Override
    public String removeFromFavoriteMeal(Long userId, Long menuId) {
        FavoriteMeal favoriteMeal = favoriteMealRepository.findFavoriteMealByUser_idAndMenu_id(userId, menuId)
                .orElseThrow(()->new FavoriteNotFoundException("favorite meal with id: " + menuId + " does not exist"));
        favoriteMealRepository.delete(favoriteMeal);
        return "favorite meal with " + menuId + " has been removed from favorite.";
    }

    public List<FavoriteMeal> getAllFavoriteMealsByAUser(Long userId) {
        return favoriteMealRepository.findByUser_id(userId);
    }

    public FavoriteMeal getAParticularFavoriteMeal(Long userId, Long menuId){
       return favoriteMealRepository.findFavoriteMealByUser_idAndMenu_id(userId, menuId)
                .orElseThrow( ()-> new FavoriteNotFoundException("Favorite meal does not exist"));
    }

}
