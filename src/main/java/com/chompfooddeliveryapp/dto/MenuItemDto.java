package com.chompfooddeliveryapp.dto;

import com.chompfooddeliveryapp.model.enums.MenuCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class MenuItemDto {

        private String name;
        private Double price;
        private String description;
        private MenuCategory category;
        private String image;


    }

