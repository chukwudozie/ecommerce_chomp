package com.chompfooddeliveryapp.repository;

import com.chompfooddeliveryapp.model.carts.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> getByUser_Id(Long userId);
    Optional<Cart> findByIdAndUser_Id(Long id, Long user_id);
}
