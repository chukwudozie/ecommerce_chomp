package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.dto.ShippingAddressDTO;
import com.chompfooddeliveryapp.payload.AllCartItems;
import com.chompfooddeliveryapp.service.serviceInterfaces.CheckoutService;
import com.chompfooddeliveryapp.service.serviceInterfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class CheckOutController {

    private final OrderService orderService;
    private final CheckoutService checkoutService;

    @Autowired
    public CheckOutController(OrderService orderService, CheckoutService checkoutService) {
        this.orderService = orderService;
        this.checkoutService = checkoutService;
    }

    @GetMapping("/{userId}/checkout")
    public ResponseEntity<AllCartItems> checkoutOrders(@PathVariable("userId") long userId) {
        var checkoutItems = orderService.checkoutCartItems(userId);
        return ResponseEntity.ok().body(checkoutItems);
    }

    public ResponseEntity<List<ShippingAddressDTO>> getUserAddress(@PathVariable long userId) {
        return ResponseEntity.ok().body(checkoutService.getAllAddress(userId));
    }

    @GetMapping("/new/order/{userId}/{cartId}")
    public ResponseEntity<?> createOrder(@PathVariable("userId") long userId, @PathVariable("cartId") long cartId) {
        return ResponseEntity.ok().body(checkoutService.createOrderFromCartItem(userId, cartId));
    }
}
