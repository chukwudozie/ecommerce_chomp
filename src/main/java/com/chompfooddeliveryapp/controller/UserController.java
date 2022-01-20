package com.chompfooddeliveryapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/docker/hello")
public class UserController {
    @GetMapping
    public String hello() {
        return "hello youtube";
    }

}
