package com.chompfooddeliveryapp.repository;

import com.chompfooddeliveryapp.model.enums.MenuCategory;
import com.chompfooddeliveryapp.model.meals.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long>{

    Optional<MenuItem> findMenuItemById(Long id);
    void deleteMenuItemById(Long id);
    Page<MenuItem> findAll(Pageable pageable);
    boolean existsByName(String name);
    @Query("SELECT m.name FROM MenuItem m WHERE m.id = :id")
    Optional<String> findNameById(@Param("id")Long id);
    Page<MenuItem> findAllByNameContains(String keyword, Pageable pageable);
    Page<MenuItem> findAllByCategory(MenuCategory category, Pageable pageable);
}
