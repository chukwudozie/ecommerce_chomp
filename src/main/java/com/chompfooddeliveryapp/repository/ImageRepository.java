package com.chompfooddeliveryapp.repository;

import com.chompfooddeliveryapp.model.meals.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Images, Long> {
    Optional<Images> findImagesByImageURL(String imageURL);
}
