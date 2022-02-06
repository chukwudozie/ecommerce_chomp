package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.payload.UserFetchAllMealsResponse;
import com.chompfooddeliveryapp.repository.MenuItemRepository;
import com.chompfooddeliveryapp.service.serviceInterfaces.MenuItemService;
import com.chompfooddeliveryapp.exception.MenuNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.chompfooddeliveryapp.model.meals.MenuItem;


import java.util.List;

@Service
public class MenuServiceImplementation implements MenuItemService {

    private MenuItemRepository menuItemRepository;

    public MenuServiceImplementation(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }


    @Override
    public MenuItem addMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItem updateMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);

    }

    @Override
    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findMenuItemById(id).orElseThrow(()->new MenuNotFoundException("item by" +  id + "was not found"));
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    @Override
    public void deleteMenuItemById(Long id) {
        menuItemRepository.deleteMenuItemById(id);
    }

    @Override
    public UserFetchAllMealsResponse fetchAllMeals(Integer pageNo, Integer pageSize, String sortBy) {
        return getUserFetchAllMealsResponse(pageNo, pageSize, sortBy, menuItemRepository);
    }

    private UserFetchAllMealsResponse getUserFetchAllMealsResponse(Integer pageNo, Integer pageSize, String sortBy, MenuItemRepository menuItemRepository) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<MenuItem> menuItems = menuItemRepository.findAll(paging);
        if(!menuItems.hasContent()) return new UserFetchAllMealsResponse("No menu Item", menuItems.getContent());
        return new UserFetchAllMealsResponse("Success", menuItems.getContent());
    }
}

