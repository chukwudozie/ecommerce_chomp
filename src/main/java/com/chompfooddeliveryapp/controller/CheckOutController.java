package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.model.meals.MenuItem;
import com.chompfooddeliveryapp.payload.AllCartItems;
import com.chompfooddeliveryapp.service.serviceInterfaces.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CheckOutController {

    private final CheckoutService checkoutService;

    public CheckOutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @GetMapping("/{userId}/checkout")
    public ResponseEntity<AllCartItems> checkoutOrders(@PathVariable("userId") long userId) {
        var checkoutItems = checkoutService.checkoutCartItems(userId);
        return ResponseEntity.ok().body(checkoutItems);
    }
}
