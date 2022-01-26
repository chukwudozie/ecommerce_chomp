package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.dto.ChangePasswordDto;
import com.chompfooddeliveryapp.dto.EditUserDetailsDto;
import com.chompfooddeliveryapp.service.serviceImpl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/profile/edit")
    public void updateUserDetails(@RequestBody EditUserDetailsDto userDetailsDto){
        userService.updateUser(userDetailsDto);
    }

    @PostMapping("/profile/edit/change_password")
    public void updatePassword(@RequestBody ChangePasswordDto changePasswordDto){
        userService.changePassword(changePasswordDto);
    }
}