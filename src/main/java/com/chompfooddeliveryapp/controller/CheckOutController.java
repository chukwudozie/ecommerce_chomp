package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.dto.ShippingAddressDTO;
import com.chompfooddeliveryapp.payload.AllCartItems;
import com.chompfooddeliveryapp.payload.CheckoutResponse;
import com.chompfooddeliveryapp.service.serviceInterfaces.CheckoutService;
import com.chompfooddeliveryapp.service.serviceInterfaces.OrderService;
import com.chompfooddeliveryapp.service.serviceInterfaces.UserServiceInterface;
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
    private final UserServiceInterface userServiceInterface;

    @Autowired
    public CheckOutController(OrderService orderService, CheckoutService checkoutService, UserServiceInterface userServiceInterface) {
        this.orderService = orderService;
        this.checkoutService = checkoutService;
        this.userServiceInterface = userServiceInterface;
    }

    @GetMapping("/checkout")
    public ResponseEntity<AllCartItems> checkoutOrders() {
        var checkoutItems = orderService.checkoutCartItems(userServiceInterface.getUserIDFromSecurityContext());
        return ResponseEntity.ok().body(checkoutItems);
    }

    public ResponseEntity<List<ShippingAddressDTO>> getUserAddress() {
        return ResponseEntity.ok().body(checkoutService.getAllAddress(userServiceInterface.getUserIDFromSecurityContext()));
    }

    @GetMapping("/new/order/{cartId}")
    public ResponseEntity<CheckoutResponse> createOrder( @PathVariable("cartId") long cartId) {
        return ResponseEntity.ok().body(checkoutService.createOrderFromCartItem(userServiceInterface.getUserIDFromSecurityContext(), cartId));
    }
}
