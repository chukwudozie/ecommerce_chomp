package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.dto.SignupDto;
import com.chompfooddeliveryapp.dto.UserDto;
import com.chompfooddeliveryapp.service.serviceImpl.SignupErrorValidationService;
import com.chompfooddeliveryapp.service.serviceImpl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    private final UserServiceImpl userServiceImpl;
    private final SignupErrorValidationService validationService;

    @Autowired
    public AuthController(UserServiceImpl userServiceImpl, SignupErrorValidationService validationService) {
        this.userServiceImpl = userServiceImpl;
        this.validationService = validationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupDto signupDto, BindingResult result) {
        log.info("I got into signup");
        System.out.println("auth controller");
        ResponseEntity<?> errorMap = validationService.validate(result);
        if(errorMap != null) return errorMap;
        return userServiceImpl.createUser(signupDto);

    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirm(@RequestParam("token") String token) {
        return userServiceImpl.confirmToken(token);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser (@RequestBody UserDto loginRequest, HttpServletResponse response) throws Exception {

        return userServiceImpl.loginUser(loginRequest, response);
     }



}
