package com.chompfooddeliveryapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
@ControllerAdvice
public class ChompExceptionHandler {
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Object> apiRequestHandler(BadRequestException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
       ExceptionPayLoad exception =  new ExceptionPayLoad(e.getMessage(),
                e,
               badRequest,
                ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(exception, badRequest);
    }

    @ExceptionHandler(value = UserNotAuthorizedException.class)
    public ResponseEntity<Object> apiUnauthorizedRequestHandler(UserNotAuthorizedException e){
        HttpStatus badRequest = HttpStatus.UNAUTHORIZED;

        ExceptionPayLoad exception =  new ExceptionPayLoad(e.getMessage(),
                e,
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(exception, badRequest);
    }

}
