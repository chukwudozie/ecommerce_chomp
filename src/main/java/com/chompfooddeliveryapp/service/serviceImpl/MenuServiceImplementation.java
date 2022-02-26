package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.dto.MenuItemDto;
import com.chompfooddeliveryapp.exception.MenuException;
import com.chompfooddeliveryapp.model.enums.MenuCategory;
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


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class MenuServiceImplementation implements MenuItemService {

    private MenuItemRepository menuItemRepository;

    public MenuServiceImplementation(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }


    @Override
    public MenuItem addMenuItem(MenuItemDto menuItemDto) {
        if(menuItemRepository.existsByName(menuItemDto.getName())){
            throw new MenuException("Menu already exists");
        }
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemDto.getName());
        menuItem.setCategory(menuItemDto.getCategory());
        menuItem.setPrice(menuItemDto.getPrice());
        menuItem.setDescription(menuItemDto.getDescription());
        menuItem.setImage(menuItemDto.getImage());
        return menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItem updateMenuItem(Long id, MenuItemDto menuItemDto) {

        MenuItem menuItem = getMenuItemById(id);
        if(menuItem == null){
            throw new MenuNotFoundException("menu not found");
        }
        menuItem.setName(menuItemDto.getName());
        menuItem.setDescription(menuItemDto.getDescription());
        menuItem.setCategory(menuItemDto.getCategory());
        menuItem.setImage(menuItemDto.getImage());
        menuItem.setPrice(menuItemDto.getPrice());
        return menuItemRepository.save(menuItem);

    }

    @Override
    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findMenuItemById(id).orElseThrow(()->new MenuNotFoundException("item by " +  id + "was not found"));
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    @Override
    public void deleteMenuItemById(Long id) {
        var menuItem1 = menuItemRepository.findById(id);
        if(!menuItem1.isPresent()){
            throw new MenuNotFoundException("menu not found");
        }
        menuItemRepository.deleteById(id);
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

    @Override
    public UserFetchAllMealsResponse fetchMealsByKeyWord(String keyword, int pageNo, int pageSize) {
        Page<MenuItem> menuItems = menuItemRepository.findAllByNameContains(keyword, PageRequest.of(pageNo, pageSize));
        if (!menuItems.hasContent()) return new UserFetchAllMealsResponse("No match found", menuItems.getContent());
        return new UserFetchAllMealsResponse("Match found", menuItems.getContent());
    }

    @Override
    public UserFetchAllMealsResponse fetchMealsByCategory(MenuCategory category, int pageNo, int pageSize) {
        Page<MenuItem> menuItems = menuItemRepository.findAllByCategory(category, PageRequest.of(pageNo, pageSize));
        if (!menuItems.hasContent()) return new UserFetchAllMealsResponse("Category does not exist or No items in Category", menuItems.getContent());
        return new UserFetchAllMealsResponse("Success", menuItems.getContent());
    }
}

