package com.chompfooddeliveryapp.repository;

import com.chompfooddeliveryapp.model.meals.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  MenuItemRepository extends JpaRepository<MenuItem, Long> {

}
