package com.chompfooddeliveryapp.model.carts;

import com.chompfooddeliveryapp.model.orders.Order;
import com.chompfooddeliveryapp.model.users.User;

public interface CartService {

    void createCartForUser(User user);

//    void emptyUserCartAfterOrder(User user, Order order);
}
