package com.chompfooddeliveryapp.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class PaymentDetailsDTO {
    Double itemsTotal;
    Double deliveryFee;
    Double total;


    @Override
    public String toString() {
        return "PaymentDetailsDTO { " +
                "itemsTotal: " + itemsTotal +
                ", deliveryFee: " + deliveryFee +
                ", total: " + total +
                "}";
    }
}
