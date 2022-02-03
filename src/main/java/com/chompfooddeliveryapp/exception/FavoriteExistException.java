package com.chompfooddeliveryapp.exception;

public class FavoriteExistException extends RuntimeException{
    public FavoriteExistException(String message){
        super(message);
    }
}
