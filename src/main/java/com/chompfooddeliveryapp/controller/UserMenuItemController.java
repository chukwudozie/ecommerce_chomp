package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.payload.UserFetchAllMealsResponse;
import com.chompfooddeliveryapp.service.serviceInterfaces.MenuItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class UserMenuItemController {

    private final MenuItemService menuItemService;

    @GetMapping
    public ResponseEntity<?> fetchAllProducts(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ){
        UserFetchAllMealsResponse userFetchAllMealsResponse = menuItemService.fetchAllMeals(pageNo, pageSize, sortBy);
        return ResponseEntity.ok(userFetchAllMealsResponse);
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<?> fetchOneProduct(@PathVariable(name = "menuId") Long menuId){
        return ResponseEntity.ok(menuItemService.getMenuItemById(menuId));
    }
}