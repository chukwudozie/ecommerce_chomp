package com.chompfooddeliveryapp.model.meals;

import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "favorite_meals")
public class FavoriteMeal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long user_id;

    @Column(unique = true)
    private Long menu_id;

    public FavoriteMeal(Long user_id, Long menu_id) {
        this.user_id = user_id;
        this.menu_id = menu_id;
    }
}


