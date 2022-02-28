package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.dto.ChangePasswordDto;
import com.chompfooddeliveryapp.dto.EditUserDetailsDto;
import com.chompfooddeliveryapp.dto.UserDetailsDTO;
import com.chompfooddeliveryapp.service.serviceImpl.UserDetailsService;
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
    private final UserDetailsService userDetailsService;


    @PostMapping("/edit")

    public ResponseEntity<?> updateUserDetails(@RequestBody EditUserDetailsDto userDetailsDto){
        userService.updateUser(userDetailsDto, userService.getUserIDFromSecurityContext());
           return new ResponseEntity<>(HttpStatus.OK);

    }


    @PostMapping("/change_password")
    public ResponseEntity<?> updatePassword(@RequestBody ChangePasswordDto changePasswordDto){
         userService.changePassword(changePasswordDto, userService.getUserIDFromSecurityContext());
            return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/details")
    public ResponseEntity<?> getAllUserDetails() throws Exception{
        System.out.println("into method");
        UserDetailsDTO userDetailsDTO = userDetailsService.getUserDetails(userService.getUserIDFromSecurityContext());
        return new ResponseEntity<>(userDetailsDTO,HttpStatus.OK);
    }

}