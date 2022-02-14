package com.chompfooddeliveryapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessPaymentRequest {

    private String paymentMethod;
//    private Long amount;
}
