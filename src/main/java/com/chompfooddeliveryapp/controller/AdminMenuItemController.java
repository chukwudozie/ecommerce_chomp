package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.dto.ChangePasswordDto;
import com.chompfooddeliveryapp.exception.MenuException;
import com.chompfooddeliveryapp.exception.MenuNotFoundException;
import com.chompfooddeliveryapp.service.serviceImpl.MenuServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.chompfooddeliveryapp.model.meals.MenuItem;




@RestController
@RequestMapping("/admin")
public class AdminMenuItemController {

    private final MenuServiceImplementation menuServiceImplementation;

    @Autowired
    public AdminMenuItemController(MenuServiceImplementation menuServiceImplementation) {
        this.menuServiceImplementation = menuServiceImplementation;

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


}
