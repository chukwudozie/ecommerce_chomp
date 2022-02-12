package com.chompfooddeliveryapp.repository;

import com.chompfooddeliveryapp.model.users.ShippingAddress;
import com.chompfooddeliveryapp.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {
    Optional<ShippingAddress> findByUserAndDefaultAddress(User user, boolean defaultAddress);
}
