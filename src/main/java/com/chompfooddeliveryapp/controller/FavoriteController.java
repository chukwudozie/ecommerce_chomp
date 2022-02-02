package com.chompfooddeliveryapp.controller;


import com.chompfooddeliveryapp.exception.FavoriteNotFoundException;
import com.chompfooddeliveryapp.model.meals.FavoriteMeal;
import com.chompfooddeliveryapp.service.serviceInterfaces.FavoriteMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/auth")
public class FavoriteController {
    private final FavoriteMealService favoriteMealService;

    @Autowired
    public FavoriteController(FavoriteMealService favoriteMealService) {
        this.favoriteMealService = favoriteMealService;
    }


    @PostMapping("/addfavoritemeal")
    public ResponseEntity<FavoriteMeal> createFavoriteMeal(@RequestBody FavoriteMeal favoriteMeal){
        final FavoriteMeal favoriteMeal1 = favoriteMealService.createFavoriteMeal(favoriteMeal);

        return new ResponseEntity<>(favoriteMeal1, HttpStatus.OK);
    }

    @PostMapping("/deleteFavoriteMeal")
    public ResponseEntity<String> deleteFavoriteMeal(@RequestBody Long userId, Long menuId){
        favoriteMealService.removeFromFavoriteMeal(userId, menuId);

        return new ResponseEntity<>("favorite meal with " + menuId + " has been deleted.", HttpStatus.OK);
    }

    @PostMapping("/allfavoritemeals")
    public ResponseEntity<List<FavoriteMeal>> getAllFavoriteMealsByAUser(Long userId) {
        return new ResponseEntity<>(favoriteMealService.getAllFavoriteMealsByAUser(userId), HttpStatus.OK);
    }

    @PostMapping("/getparticularfavoritemeal")
    public ResponseEntity<FavoriteMeal> getParticularFavoriteMeal(Long userId, Long mealId){
        return new ResponseEntity<>(favoriteMealService.getAParticularFavoriteMeal(userId, mealId), HttpStatus.OK);
    }


}
