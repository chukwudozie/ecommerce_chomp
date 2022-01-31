package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.dto.ChangePasswordDto;
import com.chompfooddeliveryapp.dto.EditUserDetailsDto;
import com.chompfooddeliveryapp.service.serviceImpl.UserServiceImpl;
import com.chompfooddeliveryapp.service.serviceInterfaces.UserServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceInterface userService;

    @PostMapping("/edit/{id}")

    public ResponseEntity<?> updateUserDetails(@RequestBody EditUserDetailsDto userDetailsDto, @PathVariable Long id){
        userService.updateUser(userDetailsDto, id);
           return new ResponseEntity<>(HttpStatus.OK);
    }




    @PostMapping("/change_password/{id}")
    public ResponseEntity<?> updatePassword(@RequestBody ChangePasswordDto changePasswordDto, @PathVariable Long id){
         userService.changePassword(changePasswordDto, id);
            return new ResponseEntity<>(HttpStatus.OK);
    }
}