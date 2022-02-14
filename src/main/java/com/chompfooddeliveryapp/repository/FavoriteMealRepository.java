package com.chompfooddeliveryapp.repository;

import com.chompfooddeliveryapp.model.meals.FavoriteMeal;
import com.chompfooddeliveryapp.model.meals.MenuItem;
import com.chompfooddeliveryapp.model.users.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteMealRepository extends JpaRepository<FavoriteMeal, Long> {

    @Query(value = "select * from favorite_meals where user_id = ?1", nativeQuery = true)
    List<FavoriteMeal> findAllByUserid(Long userId);

    Optional<FavoriteMeal> findFavoriteMealByUseridAndMenuid(User userId, MenuItem menuId);

    @Query("SELECT count(fm.menuid) as ct FROM FavoriteMeal fm group by fm.menuid order by ct desc")
    List<Long> findTopByMenuidOrderByMenuidDesc(Pageable pageable);
}
