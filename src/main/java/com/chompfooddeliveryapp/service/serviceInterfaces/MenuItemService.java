package com.chompfooddeliveryapp.service.serviceInterfaces;

import java.util.List;
//import java.util.Optional;
import com.chompfooddeliveryapp.model.enums.MenuCategory;
import com.chompfooddeliveryapp.model.meals.MenuItem;
import com.chompfooddeliveryapp.payload.UserFetchAllMealsResponse;


public interface MenuItemService {

    MenuItem addMenuItem(MenuItem menuItem);
    MenuItem updateMenuItem(Long id, MenuItem menuItem);
    MenuItem getMenuItemById(Long id);
    List<MenuItem> getAllMenuItems();
    void deleteMenuItemById(Long id);
    UserFetchAllMealsResponse fetchAllMeals(Integer pageNo, Integer pageSize, String sortBy);
    UserFetchAllMealsResponse fetchMealsByKeyWord(String keyword, int pageNo, int pageSize);
    UserFetchAllMealsResponse fetchMealsByCategory(MenuCategory category, int pageNo, int pageSize);







}
