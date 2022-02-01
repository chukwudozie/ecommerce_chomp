package com.chompfooddeliveryapp.model.meals;

import com.chompfooddeliveryapp.model.enums.MenuCategory;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "menu_items")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String image;

    private String desciption;

    @NotNull
    private Long price;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private MenuCategory category;

    @NotNull
    private Timestamp dateCreated;
}
