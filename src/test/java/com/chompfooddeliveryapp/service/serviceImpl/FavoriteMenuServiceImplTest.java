package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.dto.SignupDto;
import com.chompfooddeliveryapp.exception.FavoriteNotFoundException;
import com.chompfooddeliveryapp.model.enums.UserRole;
import com.chompfooddeliveryapp.model.meals.FavoriteMeal;
import com.chompfooddeliveryapp.repository.FavoriteMealRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FavoriteMenuServiceImplTest {
    @Mock
    FavoriteMealRepository favoriteMealRepository;

    @InjectMocks
    FavoriteMenuServiceImpl favoriteMenuService;

    @BeforeEach
    void setUp() {
        favoriteMenuService = new FavoriteMenuServiceImpl(favoriteMealRepository);
    }
    FavoriteMeal favoriteMeal = new FavoriteMeal();

    @Test
    void createFavoriteMeal() {


        favoriteMeal.setId(1L);
        favoriteMeal.setUser_id(1L);
        favoriteMeal.setMenu_id(23L);

        when(favoriteMealRepository.findByMenu_idAndUser_id(favoriteMeal.getMenu_id(),favoriteMeal.getUser_id())).thenReturn(false);
        favoriteMenuService.createFavoriteMeal(favoriteMeal);
        verify(favoriteMealRepository, times(1)).save(any());

    }

//    @Test
//    void deleteFavoriteMeal() {
//        favoriteMeal.setId(1L);
//        favoriteMeal.setUser_id(1L);
//        favoriteMeal.setMenu_id(23L);
//
//        when(favoriteMealRepository.findByMenu_idAndUser_id(favoriteMeal.getMenu_id(),favoriteMeal.getUser_id())).thenReturn(false);
//        favoriteMenuService.createFavoriteMeal(favoriteMeal);
//        when(favoriteMealRepository.findByMenu_idAndUser_id(favoriteMeal.getMenu_id(),favoriteMeal.getUser_id())).thenReturn(true);
//        favoriteMenuService.removeFromFavoriteMeal(favoriteMeal.getMenu_id(),favoriteMeal.getUser_id());
//        verify(favoriteMealRepository, times(1)).delete(favoriteMeal);
//
//    }

//    @Test
//    void getAllFavoriteMealsByAUser() {
//    }
//
//    @Test
//    void getAParticularFavoriteMeal() {
//    }
}