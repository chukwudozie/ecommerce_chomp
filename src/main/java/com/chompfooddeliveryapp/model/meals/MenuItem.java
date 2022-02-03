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

    @NotNull
    private String description;

    @NotNull
    private Long price;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private MenuCategory category;

    @NotNull
    private Timestamp dateCreated;

    public MenuItem(String name, String image, String description, Long price, MenuCategory category, Timestamp dateCreated) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.category = category;
        this.dateCreated = dateCreated;
    }
}
