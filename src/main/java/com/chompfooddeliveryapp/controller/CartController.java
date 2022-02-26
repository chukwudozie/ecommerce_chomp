package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.dto.CartDTO;
import com.chompfooddeliveryapp.service.serviceInterfaces.CartService;
import com.chompfooddeliveryapp.service.serviceInterfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class CartController {
    private final CartService cartService;
    private final UserServiceInterface userServiceInterface;

    @Autowired
    public CartController(CartService cartService, UserServiceInterface userServiceInterface) {
        this.cartService = cartService;
        this.userServiceInterface = userServiceInterface;
    }

    @PostMapping("/add/{menuId}")
    public ResponseEntity<?> addToCart(@RequestBody CartDTO cartDTO,
              @PathVariable Long menuId){
       return cartService.addToCart(cartDTO,userServiceInterface.getUserIDFromSecurityContext(),menuId);
    }

    @PostMapping("/updatecartitemquantity/{menuId}/{quantity}")
    public ResponseEntity<?> updateCartItem( @PathVariable Long menuId, @PathVariable Integer quantity){
        return cartService.updateCartQuantity(userServiceInterface.getUserIDFromSecurityContext(),menuId, quantity);
    }

    @DeleteMapping("/delete/{menuId}")
        public ResponseEntity<?> deleteProductFromCart( @PathVariable Long menuId){
            return cartService.deleteCartItem(userServiceInterface.getUserIDFromSecurityContext(),menuId);
        }

        @GetMapping("/viewCart")
    public ResponseEntity<?> viewCartItems(){
        return cartService.findAllProductsByUser(userServiceInterface.getUserIDFromSecurityContext());
        }
    }

