package com.chompfooddeliveryapp.model.carts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add/{userId}/{menuId}")
    public ResponseEntity<?> addToCart(@RequestBody CartDTO cartDTO,
             @PathVariable Long userId, @PathVariable Long menuId){
       return cartService.addToCart(cartDTO,userId,menuId);
//        return ResponseEntity.ok()
    }
}
