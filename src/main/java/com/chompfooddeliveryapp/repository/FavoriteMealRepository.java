package com.chompfooddeliveryapp.repository;

import com.chompfooddeliveryapp.model.meals.FavoriteMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface FavoriteMealRepository extends JpaRepository<FavoriteMeal, Long> {
    boolean findByMenu_idAndUser_id(Long menuId, Long user_id);

    List<FavoriteMeal> findByUser_id(Long userId);
}
