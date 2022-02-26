package com.chompfooddeliveryapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class OrderSummaryDTO {
    private String name;
    private String image;
    private Double price;
    private Long quantity;
}
