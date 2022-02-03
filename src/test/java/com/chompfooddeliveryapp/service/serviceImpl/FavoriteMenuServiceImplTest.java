package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.model.meals.FavoriteMeal;
import com.chompfooddeliveryapp.repository.FavoriteMealRepository;
import com.chompfooddeliveryapp.repository.MenuItemRepository;
import com.chompfooddeliveryapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class FavoriteMenuServiceImplTest {
    @Mock
    FavoriteMealRepository favoriteMealRepository;

    @Mock
    MenuItemRepository menuItemRepository;

    @Mock
    UserRepository userRepository;

    FavoriteMenuServiceImpl favoriteMenuService;

    @BeforeEach
    void setUp() {
        favoriteMenuService = new FavoriteMenuServiceImpl(favoriteMealRepository, menuItemRepository, userRepository);
    }
    FavoriteMeal favoriteMeal = new FavoriteMeal();


}