package com.chompfooddeliveryapp.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerificationResponse {
    private String status;
    private String message;
    private Object amount;
    private Object paymentDate;
}
