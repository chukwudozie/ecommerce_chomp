package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.model.enums.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class TestController {


    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createProduct(){
        System.out.println("User with Admin authority Entered here");
        return new ResponseEntity<>("Product Created only by admin ", HttpStatus.CREATED);
    }

    @GetMapping("/consume")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> consumeProduct(){
        System.out.println("User with user authority entered here");
        return new ResponseEntity<>("Product Consumed only by a user", HttpStatus.CREATED);
    }



}
