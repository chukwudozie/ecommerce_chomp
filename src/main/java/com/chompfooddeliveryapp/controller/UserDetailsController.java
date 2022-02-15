package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.dto.UserDetailsDTO;
import com.chompfooddeliveryapp.service.serviceImpl.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserDetailsController {

    private final UserDetailsService userDetailsService;

    @Autowired
    public UserDetailsController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @GetMapping("/details/{userId}")
    public ResponseEntity<?> getAllUserDetails(@PathVariable Long userId) throws Exception{
        System.out.println("into method");
       UserDetailsDTO userDetailsDTO = userDetailsService.getUserDetails(userId);
        return new ResponseEntity<>(userDetailsDTO,HttpStatus.OK);
    }




}
