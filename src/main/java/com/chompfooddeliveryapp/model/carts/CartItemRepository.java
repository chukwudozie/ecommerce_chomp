package com.chompfooddeliveryapp.model.carts;

import com.chompfooddeliveryapp.model.meals.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByMenuId(MenuItem menu);
}
