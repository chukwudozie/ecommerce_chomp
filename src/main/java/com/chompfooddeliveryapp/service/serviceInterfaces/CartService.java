package com.chompfooddeliveryapp.service.serviceInterfaces;


import com.chompfooddeliveryapp.model.carts.CartDTO;
import com.chompfooddeliveryapp.model.carts.ViewCartResponse;
import com.chompfooddeliveryapp.dto.CartDTO;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.payload.AllCartItems;
import com.chompfooddeliveryapp.payload.ViewCartResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {

    void createCartForUser(User user);

//    void emptyUserCartAfterOrder(User user, Order order);

    ResponseEntity<?> addToCart(CartDTO cartDTO, Long userId, Long menuId);

    ResponseEntity<?> reduceCartItemQty(Long userId, Long menuId);

    ResponseEntity<?> deleteCartItem(Long userId, Long menuId);
    ResponseEntity<AllCartItems> findAllProductsByUser(Long userId);

//    public ResponseEntity<List<CartItem>> findAllProductsByUser(Long cartId);
}
