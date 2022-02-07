package com.chompfooddeliveryapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Product Not Found")
public class ProductNotFoundException extends RuntimeException{
    private String message;

    public ProductNotFoundException() {
        this.message = "";
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
