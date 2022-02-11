package com.chompfooddeliveryapp.controller;


import com.chompfooddeliveryapp.dto.ResponseViewUserOrdersDTO;
import com.chompfooddeliveryapp.dto.ShippingAddressDTO;
import com.chompfooddeliveryapp.model.users.ShippingAddress;
import com.chompfooddeliveryapp.service.serviceInterfaces.CheckoutService;
import com.chompfooddeliveryapp.service.serviceInterfaces.ViewOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class ViewAllOrdersController {

    private final ViewOrderService viewOrderService;
    private final CheckoutService checkoutService;

    public ViewAllOrdersController(ViewOrderService viewOrderService, CheckoutService checkoutService) {
        this.viewOrderService = viewOrderService;
        this.checkoutService = checkoutService;
    }




    @PostMapping("/{userId}/shipping-address")
    public ResponseEntity<ShippingAddress> saveShippingAddress(@PathVariable("userId") Long userId,
                                                               @RequestBody ShippingAddressDTO shippingAddress) {
        var responseText = checkoutService.saveShippingAddress(userId, shippingAddress);
        return new ResponseEntity<>(responseText, HttpStatus.OK);
    }

    @GetMapping("/{userId}/view-all-orders")
    public ResponseEntity<ResponseViewUserOrdersDTO> viewAllOrders(@PathVariable("userId") long userId) {
        var allUserOrders = viewOrderService.getAllOrdersByUserId(userId);
        return new ResponseEntity<>(allUserOrders, HttpStatus.OK);
    }
}
