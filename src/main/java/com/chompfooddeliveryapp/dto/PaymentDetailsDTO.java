package com.chompfooddeliveryapp.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class PaymentDetailsDTO {
    Long itemsTotal;
    Long deliveryFee;
    Long total;


    @Override
    public String toString() {
        return "PaymentDetailsDTO { " +
                "itemsTotal: " + itemsTotal +
                ", deliveryFee: " + deliveryFee +
                ", total: " + total +
                "}";
    }
}
