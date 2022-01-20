package com.chompfooddeliveryapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/")
public class UserController {
    
    @GetMapping("/test")
    public String test(){
        return "Test passed.";
    }
}