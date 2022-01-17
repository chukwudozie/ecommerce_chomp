package com.chompfooddeliveryapp.model.meals;

import com.chompfooddeliveryapp.model.enums.MenuCategory;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "menu_items")
public class MenuItem {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String image;

    @NotNull
    private Long price;

    @NotNull
    private MenuCategory category;

    @NotNull
    private Timestamp dateCreated;
}
