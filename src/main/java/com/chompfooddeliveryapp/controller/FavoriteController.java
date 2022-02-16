package com.chompfooddeliveryapp.controller;


import com.chompfooddeliveryapp.model.meals.FavoriteMeal;
import com.chompfooddeliveryapp.model.meals.MenuItem;
import com.chompfooddeliveryapp.payload.FavouriteMealResponse;
import com.chompfooddeliveryapp.service.serviceInterfaces.FavoriteMealService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Slf4j
public class FavoriteController {

    private final FavoriteMealService favoriteMealService;
    @PostMapping("/addfavoritemeal/{userId}/{menuId}")
    public ResponseEntity<MenuItem> createFavoriteMeal(@PathVariable Long userId, @PathVariable Long menuId){


        final MenuItem menuItem = favoriteMealService.createFavoriteMeal(userId, menuId);
        return new ResponseEntity<>(menuItem, HttpStatus.OK);
    }

    @PostMapping("/deletefavoritemeal/{userId}/{menuId}")
    public ResponseEntity<String> deleteFavoriteMeal(@PathVariable Long userId, @PathVariable Long menuId){
        favoriteMealService.removeFromFavoriteMeal(userId, menuId);
        return new ResponseEntity<>("favorite meal with " + menuId + " has been removed from favorite.", HttpStatus.OK);
    }

    @GetMapping("allfavoritemeals/{userId}")
    public ResponseEntity<?> getAllFavoriteMealsByAUser(@PathVariable Long userId) {
        List<FavoriteMeal> favoriteMeals = favoriteMealService.getAllFavoriteMealsByAUser(userId);
        log.info(">>>>><<<<"+ favoriteMeals);
        FavouriteMealResponse response = new FavouriteMealResponse();
        response.setFavoriteMealList(favoriteMeals);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getparticularfavoritemeal/{userId}/{menuId}")
    public ResponseEntity<MenuItem> getParticularFavoriteMeal(@PathVariable Long userId, @PathVariable Long menuId){
        return new ResponseEntity<>(favoriteMealService.getAParticularFavoriteMeal(userId, menuId), HttpStatus.OK);
    }

}