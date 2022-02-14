package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.dto.ChangePasswordDto;
import com.chompfooddeliveryapp.exception.MenuException;
import com.chompfooddeliveryapp.exception.MenuNotFoundException;
import com.chompfooddeliveryapp.service.serviceImpl.MenuServiceImplementation;
import com.chompfooddeliveryapp.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.chompfooddeliveryapp.model.meals.MenuItem;




@RestController
@RequestMapping("/admin")
public class MenuItemController{

    private final MenuServiceImplementation menuServiceImplementation;
    private final UserServiceImpl userService;

    @Autowired
    public MenuItemController(MenuServiceImplementation menuServiceImplementation, UserServiceImpl userService) {
        this.menuServiceImplementation = menuServiceImplementation;
        this.userService = userService;
    }


    @GetMapping("/getallproducts")
    public ResponseEntity<?> getAllProducts(){
        return ResponseEntity.ok().body(menuServiceImplementation.getAllMenuItems());
    }

    @PostMapping("/additem")
    public ResponseEntity<?> addItem(@RequestBody MenuItem menuItem) throws MenuException {

         menuServiceImplementation.addMenuItem(menuItem);

        return new ResponseEntity<>("Menu item has been created", HttpStatus.CREATED);
    }

    @PutMapping("/updateitem/{id}")
    public ResponseEntity<?> updateItem(@RequestBody MenuItem menuItem, @PathVariable ("id") Long id) throws MenuNotFoundException{
        MenuItem upDatedMenuItem = menuServiceImplementation.updateMenuItem(id, menuItem);

        return new ResponseEntity<>("Menu item has been updated", HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteitem/{id}")
        public ResponseEntity<?> deleteMenuItem(@PathVariable("id") Long id) throws MenuNotFoundException{
        menuServiceImplementation.deleteMenuItemById(id);
        return new ResponseEntity<>("Menu item has deleted", HttpStatus.OK);
    }

    @PostMapping("/change_password/{id}")
    public ResponseEntity<?> updatePassword(@RequestBody ChangePasswordDto changePasswordDto, @PathVariable Long id){
        userService.changePassword(changePasswordDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
