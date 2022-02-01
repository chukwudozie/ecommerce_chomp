package com.chompfooddeliveryapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
@ControllerAdvice
public class ChompExceptionHandler {
    @ExceptionHandler(value = GlobalException.class)
    public ResponseEntity<Object> apiRequestHandler(GlobalException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
       ExceptionPayLoad exception =  new ExceptionPayLoad(e.getMessage(),
                e,
               badRequest,
                ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(exception, badRequest);
    }

}
