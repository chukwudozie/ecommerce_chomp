package com.chompfooddeliveryapp.controller;


import com.chompfooddeliveryapp.model.meals.FavoriteMeal;
import com.chompfooddeliveryapp.service.serviceInterfaces.FavoriteMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> deleteFavoriteMeal(@RequestBody Long mealId){
        favoriteMealService.deleteFavoriteMeal(mealId);

        return new ResponseEntity<>("favorite meal with " + mealId + " has been deleted.", HttpStatus.OK);
    }


}
