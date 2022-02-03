package com.chompfooddeliveryapp.repository;

import com.chompfooddeliveryapp.model.meals.FavoriteMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteMealRepository extends JpaRepository<FavoriteMeal, Long> {

    @Query("select u from FavoriteMeal u where u.menu_id = ?1 and u.user_id = ?2")
    Boolean findByMenu_idAndUser_id(Long menu_Id, Long user_id);

    @Query("select u from FavoriteMeal u where u.user_id = ?1")
    List<FavoriteMeal> findByUser_id(Long userId);

    @Query("select u from FavoriteMeal u where u.user_id = ?1 and u.menu_id = ?2")
   Optional<FavoriteMeal> findFavoriteMealByUser_idAndMenu_id(Long userId, long menuId);

}
