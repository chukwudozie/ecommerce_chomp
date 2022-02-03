package com.chompfooddeliveryapp.controller;


import com.chompfooddeliveryapp.model.meals.FavoriteMeal;
import com.chompfooddeliveryapp.service.serviceInterfaces.FavoriteMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class FavoriteController {
    private final FavoriteMealService favoriteMealService;

    @Autowired
    public FavoriteController(FavoriteMealService favoriteMealService) {
        this.favoriteMealService = favoriteMealService;
    }

    @PostMapping("/addfavoritemeal/{userId}/{menuId}")
    public ResponseEntity<FavoriteMeal> createFavoriteMeal(@PathVariable Long userId, @PathVariable Long menuId){
        final FavoriteMeal favoriteMeal1 = favoriteMealService.createFavoriteMeal(userId, menuId);

        return new ResponseEntity<>(favoriteMeal1, HttpStatus.OK);
    }

    @PostMapping("/deletefavoritemeal/{userId}/{menuId}")
    public ResponseEntity<String> deleteFavoriteMeal(@PathVariable Long userId, @PathVariable Long menuId){
        favoriteMealService.removeFromFavoriteMeal(userId, menuId);

        return new ResponseEntity<>("favorite meal with " + menuId + " has been removed from favorite.", HttpStatus.OK);
    }

    @GetMapping("/allfavoritemeals/{userId}")
    public ResponseEntity<List<FavoriteMeal>> getAllFavoriteMealsByAUser(@PathVariable Long userId) {
        return new ResponseEntity<>(favoriteMealService.getAllFavoriteMealsByAUser(userId), HttpStatus.OK);
    }

    @GetMapping("/getparticularfavoritemeal/{userId}/{menuId}")
    public ResponseEntity<FavoriteMeal> getParticularFavoriteMeal(@PathVariable Long userId, @PathVariable Long menuId){
        return new ResponseEntity<>(favoriteMealService.getAParticularFavoriteMeal(userId, menuId), HttpStatus.OK);
    }

}
