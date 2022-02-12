package com.chompfooddeliveryapp.dto;

import com.chompfooddeliveryapp.model.enums.MenuCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {

    private String name;
    private String image;
    private String description;
    private Long price;
    private MenuCategory category;
    private Timestamp dateCreated;
}
