package com.chompfooddeliveryapp.repository;

import com.chompfooddeliveryapp.model.meals.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

}

