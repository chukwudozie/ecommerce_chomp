package com.chompfooddeliveryapp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuResponse {
    private String message;

    private String productName;

    private Double price;

    private String description;

    private String category;

    private String image;

}
