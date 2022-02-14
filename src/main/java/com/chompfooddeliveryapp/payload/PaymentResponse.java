package com.chompfooddeliveryapp.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponse {

    private Object payment;
    private boolean status;
    private  String message;
}
