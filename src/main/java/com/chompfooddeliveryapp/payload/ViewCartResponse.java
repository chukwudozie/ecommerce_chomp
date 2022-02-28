package com.chompfooddeliveryapp.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewCartResponse {
    private Long productId;
    private Long cartItemId;
    private String productName;
    private String productOwner;
    private String productImage;
    private Integer productQuantity;
    private Double productPrice;
    private double amount;
}
