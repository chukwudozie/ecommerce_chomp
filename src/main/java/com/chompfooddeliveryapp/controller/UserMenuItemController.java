package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.model.enums.MenuCategory;
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

    @GetMapping("/search")
    public ResponseEntity<UserFetchAllMealsResponse> fetchByKeyword(
            @RequestParam("searchKey") String keyword,
            @RequestParam("pageNo") int pageNo,
            @RequestParam("pageSize") int pageSize) {
        UserFetchAllMealsResponse mealsResponse = menuItemService.fetchMealsByKeyWord(keyword, pageNo, pageSize);
        return ResponseEntity.ok(mealsResponse);
    }

    @GetMapping("/category")
    public ResponseEntity<UserFetchAllMealsResponse> fetchProductsByCategory(
            @RequestParam("category") MenuCategory category,
            @RequestParam("pageNo") int pageNo,
            @RequestParam("pageSize") int pageSize) {
        UserFetchAllMealsResponse userFetchAllMealsResponse = menuItemService.fetchMealsByCategory(category, pageNo, pageSize);
        return ResponseEntity.ok(userFetchAllMealsResponse);
    }
}