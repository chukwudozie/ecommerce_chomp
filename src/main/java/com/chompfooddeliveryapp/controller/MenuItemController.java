package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.service.serviceImpl.MenuServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.chompfooddeliveryapp.model.meals.MenuItem;




@RestController
//@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/admin")
public class MenuItemController{

    private final MenuServiceImplementation menuServiceImplementation;

    @Autowired
    public MenuItemController(MenuServiceImplementation menuServiceImplementation) {
        this.menuServiceImplementation = menuServiceImplementation;
    }


    @GetMapping("/getallproducts")
    public ResponseEntity<?> getallProducts(){
        return ResponseEntity.ok().body(menuServiceImplementation.getAllMenuItems());
    }


    @PostMapping("/additem")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<MenuItem> addItem(@RequestBody MenuItem menuItem){
        MenuItem newMenuItem = menuServiceImplementation.addMenuItem(menuItem);
        System.out.println(newMenuItem);
        return new ResponseEntity<>(newMenuItem, HttpStatus.CREATED);
    }

    @PutMapping("/updateitem")
    public ResponseEntity<MenuItem> upDateItem(@RequestBody MenuItem menuItem){
        MenuItem upDatedMenuItem = menuServiceImplementation.updateMenuItem(menuItem);
        System.out.println(upDatedMenuItem);
        return new ResponseEntity<>(upDatedMenuItem, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteitem/{id}")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable("id") Long id){
        menuServiceImplementation.deleteMenuItemById(id);
        return new ResponseEntity<>(HttpStatus.OK);
      

    }


}
