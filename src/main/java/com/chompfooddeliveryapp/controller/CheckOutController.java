package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.dto.ShippingAddressDTO;
import com.chompfooddeliveryapp.payload.AllCartItems;
import com.chompfooddeliveryapp.service.serviceInterfaces.CheckoutService;
import com.chompfooddeliveryapp.service.serviceInterfaces.ViewOrderService;
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

    private final ViewOrderService viewOrderService;
    private final CheckoutService checkoutService;

    @Autowired
    public CheckOutController(ViewOrderService viewOrderService, CheckoutService checkoutService) {
        this.viewOrderService = viewOrderService;
        this.checkoutService = checkoutService;
    }

    @GetMapping("/{userId}/checkout")
    public ResponseEntity<AllCartItems> checkoutOrders(@PathVariable("userId") long userId) {
        var checkoutItems = viewOrderService.checkoutCartItems(userId);
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
