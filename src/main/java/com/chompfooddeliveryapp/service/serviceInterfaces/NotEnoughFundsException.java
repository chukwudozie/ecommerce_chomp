package com.chompfooddeliveryapp.service.serviceInterfaces;

public class NotEnoughFundsException extends RuntimeException {
    public NotEnoughFundsException(String message) {
        super(message);
    }

}
