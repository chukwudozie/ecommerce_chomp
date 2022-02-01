package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.exception.FavoriteExistException;
import com.chompfooddeliveryapp.exception.FavoriteNotFoundException;
import com.chompfooddeliveryapp.model.meals.FavoriteMeal;
import com.chompfooddeliveryapp.repository.FavoriteMealRepository;
import com.chompfooddeliveryapp.service.serviceInterfaces.FavoriteMealService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FavoriteMenuServiceImpl implements FavoriteMealService {

    private final FavoriteMealRepository favoriteMealRepository;

    @Autowired
    public FavoriteMenuServiceImpl(FavoriteMealRepository favoriteMealRepository) {
        this.favoriteMealRepository = favoriteMealRepository;
    }

    @Override
    public FavoriteMeal createFavoriteMeal(FavoriteMeal favoriteMeal) {
        if(favoriteMealRepository.findByMenu_idAndUser_id(favoriteMeal.getMenu_id(),favoriteMeal.getUser_id())){
            throw new FavoriteExistException("Favorite has already been created by user.");

        }
        return favoriteMealRepository.save(favoriteMeal);
    }

    @Override
    public String deleteFavoriteMeal(Long mealId) {
        FavoriteMeal favoriteMeal = favoriteMealRepository.findById(mealId).orElseThrow(()->new FavoriteNotFoundException("favorite meal with id: " + mealId + " does not exist"));
        favoriteMealRepository.delete(favoriteMeal);
        return "favorite meal with " + mealId + " has been deleted.";
    }

    public List<FavoriteMeal> getAllFavoriteMealsByAUser(Long userId) {
        return favoriteMealRepository.findByUser_id(userId);
    }

    public FavoriteMeal getAParticularFavoriteMeal(Long userId, Long mealId){
       return favoriteMealRepository.findFavoriteMealByUser_idAndMenu_id(userId, mealId)
                .orElseThrow( ()-> new FavoriteNotFoundException("Favorite meal does not exist"));
    }

}
