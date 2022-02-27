package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.dto.ResponseViewUserOrdersDTO;
import com.chompfooddeliveryapp.dto.ShippingAddressDTO;
import com.chompfooddeliveryapp.exception.BadRequestException;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.security.service.UserDetailsImpl;
import com.chompfooddeliveryapp.service.serviceInterfaces.CheckoutService;
import com.chompfooddeliveryapp.service.serviceInterfaces.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserOrderController {

    private final OrderService orderService;
    private final CheckoutService checkoutService;
    private final AuthenticationManager authenticationManager;
    @Autowired
    public UserOrderController(OrderService orderService, CheckoutService checkoutService, AuthenticationManager authenticationManager) {
        this.orderService = orderService;
        this.checkoutService = checkoutService;
        this.authenticationManager=authenticationManager;
    }


    @GetMapping("/viewOrderDetails/{orderId}")
    public ResponseEntity<?> viewOrderDetails(@PathVariable("orderId") Long orderId){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();

        return new ResponseEntity<>(orderService.getOrderDetails(user.getId(), orderId), HttpStatus.OK);
    }

    @PostMapping("/{userId}/shipping-address")
    public ResponseEntity<List<String>> saveShippingAddress(@PathVariable("userId") Long userId,
                                                            @RequestBody ShippingAddressDTO shippingAddress) {
        var responseText = checkoutService.saveShippingAddress(userId, shippingAddress);
        return new ResponseEntity<>(responseText, HttpStatus.OK);
    }

    @GetMapping("/{userId}/view-all-orders")
    public ResponseEntity<ResponseViewUserOrdersDTO> viewAllOrders(@PathVariable("userId") long userId) {
        var allUserOrders = orderService.getAllOrdersByUserId(userId);
        return new ResponseEntity<>(allUserOrders, HttpStatus.OK);
    }

}
