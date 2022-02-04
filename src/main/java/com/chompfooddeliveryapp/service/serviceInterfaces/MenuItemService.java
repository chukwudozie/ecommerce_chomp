package com.chompfooddeliveryapp.service.serviceInterfaces;

import java.util.List;
//import java.util.Optional;
import com.chompfooddeliveryapp.model.meals.MenuItem;


public interface MenuItemService {

    public MenuItem addMenuItem(MenuItem menuItem);
    public MenuItem updateMenuItem(MenuItem menuItem);
    public MenuItem getMenuItemById(Long id);
    public List<MenuItem> getAllMenuItems();
    public void deleteMenuItemById(Long id);

}
