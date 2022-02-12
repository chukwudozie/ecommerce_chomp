package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.dto.AdminViewOrderDTO;
import com.chompfooddeliveryapp.exception.ProductNotFoundException;
import com.chompfooddeliveryapp.model.meals.MenuItem;
import com.chompfooddeliveryapp.payload.UserFetchAllMealsResponse;
import com.chompfooddeliveryapp.repository.MenuItemRepository;
import com.chompfooddeliveryapp.service.serviceImpl.OrderServiceImplementation;
import com.chompfooddeliveryapp.service.serviceInterfaces.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminFetchProductsController {
    private final MenuItemService service;
    private final MenuItemRepository menuRepo;
    private final OrderServiceImplementation orderServiceImplementation;

    @Autowired
    public AdminFetchProductsController(MenuItemService service, MenuItemRepository repo,
                                        OrderServiceImplementation orderServiceImplementation) {
        this.service = service;
        menuRepo = repo;
        this.orderServiceImplementation = orderServiceImplementation;
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<MenuItem> getProduct(@PathVariable(name = "productId") Long prodId){

       MenuItem item = menuRepo.findById(prodId).
               orElseThrow(()->
               new ProductNotFoundException("Product Not Found on Id: "+prodId));
        return ResponseEntity.ok(item);
    }

    @GetMapping("/products")
    public ResponseEntity<?> getProductsByPage(@RequestParam(name = "page", defaultValue = "0") int page,
                                               @RequestParam(name = "size", defaultValue = "10") int size){

        UserFetchAllMealsResponse mealsPage = service.fetchAllMeals(page, size, "name");
        return ResponseEntity.ok(mealsPage);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<AdminViewOrderDTO>> viewOrdersHistory() {
        return ResponseEntity.ok().body(orderServiceImplementation.fetchAllOrdersToAdminDashboard());
    }
}
