package com.chompfooddeliveryapp.controller;


import com.chompfooddeliveryapp.configuration.CloudinaryConfig;
import com.chompfooddeliveryapp.dto.MenuItemDto;
import com.chompfooddeliveryapp.exception.MenuException;
import com.chompfooddeliveryapp.exception.MenuNotFoundException;
import com.chompfooddeliveryapp.service.serviceImpl.MenuServiceImplementation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.chompfooddeliveryapp.model.meals.MenuItem;

import java.io.IOException;


@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminMenuItemController {

    private final MenuServiceImplementation menuServiceImplementation;

    private final Logger logger = LoggerFactory.getLogger(AdminMenuItemController.class);

    @Value("${cloudinary_api_key}")
    private String cloudinary_api_key;

    @Value("${cloudinary_api_secret}")
    private String cloudinary_api_secret;

    @Value("${cloud_name}")
    private String cloud_name;

    @Autowired
    public AdminMenuItemController(MenuServiceImplementation menuServiceImplementation) {
        this.menuServiceImplementation = menuServiceImplementation;

    }


    @GetMapping("/getallproducts")
    public ResponseEntity<?> getAllProducts(){
        return ResponseEntity.ok().body(menuServiceImplementation.getAllMenuItems());
    }

    @PostMapping("/additem")
    public ResponseEntity<?> addItem(@RequestBody MenuItemDto menuItem) throws MenuException, IOException {
        log.info("This is the cloud name " + cloud_name);
        log.info("This is the cloudinary_api_key " + cloudinary_api_key);
        log.info("This is the cloud cloudinary_api_secret " + cloudinary_api_secret);

        logger.info("This is the cloud name " + cloud_name);
        logger.info("This is the cloudinary_api_key " + cloudinary_api_key);
        logger.info("This is the cloud cloudinary_api_secret " + cloudinary_api_secret);
        System.out.println("d>>>>>>>>>>>>>" + cloudinary_api_secret);

        return new ResponseEntity<>(menuServiceImplementation.addMenuItem(menuItem), HttpStatus.CREATED);
    }

    @PutMapping("/updateitem/{id}")
    public ResponseEntity<?> updateItem(@RequestBody MenuItemDto menuItemDto, @PathVariable ("id") Long id) throws MenuNotFoundException, IOException {


        return new ResponseEntity<>(menuServiceImplementation.updateMenuItem(id, menuItemDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteitem/{id}")
        public ResponseEntity<?> deleteMenuItem(@PathVariable("id") Long id) throws MenuNotFoundException{
        menuServiceImplementation.deleteMenuItemById(id);
        return new ResponseEntity<>("Menu item has deleted", HttpStatus.OK);
    }


}
