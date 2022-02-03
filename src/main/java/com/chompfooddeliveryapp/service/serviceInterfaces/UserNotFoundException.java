package com.chompfooddeliveryapp.service.serviceInterfaces;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}

