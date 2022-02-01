package com.chompfooddeliveryapp.service.serviceInterfaces;

import com.chompfooddeliveryapp.model.meals.MenuItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemService {
    public MenuItem addMenuItem(MenuItem menuItem);
    public MenuItem updateMenuItem(MenuItem menuItem);
    public MenuItem getMenuItemById(Long id);
    public List<MenuItem> getAllMenuItems();
    public void deleteMenuItemById(Long id);
}
