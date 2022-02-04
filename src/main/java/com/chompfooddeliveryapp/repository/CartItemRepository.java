package com.chompfooddeliveryapp.repository;

import com.chompfooddeliveryapp.model.carts.Cart;
import com.chompfooddeliveryapp.model.carts.CartItem;
import com.chompfooddeliveryapp.model.meals.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> , PagingAndSortingRepository<CartItem,Long> {

    Optional<CartItem> findByMenuIdAndCartId(MenuItem menu, Cart cartId);
    List<CartItem> findAllByCartId(Cart cartId);
    List<CartItem> findAllByCartId_Id(Long cartId);
    List<CartItem> findAllByMenuId_Id(Long menuId);

}
