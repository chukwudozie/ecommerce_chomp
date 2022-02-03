package com.chompfooddeliveryapp.model.carts;

import com.chompfooddeliveryapp.model.orders.Order;
import com.chompfooddeliveryapp.model.users.User;
import org.springframework.http.ResponseEntity;

public interface CartService {

    void createCartForUser(User user);

//    void emptyUserCartAfterOrder(User user, Order order);

    ResponseEntity<?> addToCart(CartDTO cartDTO,Long userId, Long menuId);
}
