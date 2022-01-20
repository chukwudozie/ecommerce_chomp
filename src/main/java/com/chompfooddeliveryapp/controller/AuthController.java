package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.dto.SignupDto;
import com.chompfooddeliveryapp.dto.UserDto;

import com.chompfooddeliveryapp.service.serviceImpl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public AuthController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupDto signupDto) throws Exception {
        log.info("I got into signup");

        return userServiceImpl.createUser(signupDto);

    }

        @PostMapping("/login")
        public ResponseEntity<?> loginUser (@RequestBody UserDto loginRequest) throws Exception {

            return userServiceImpl.loginUser(loginRequest);

    }

}
