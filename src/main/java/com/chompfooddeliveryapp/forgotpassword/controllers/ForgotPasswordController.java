package com.chompfooddeliveryapp.forgotpassword.controllers;

import com.chompfooddeliveryapp.forgotpassword.dto.UserForgetPasswordService;
import com.chompfooddeliveryapp.forgotpassword.dto.UserForgotPasswordDto;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path="forgot-passowrd")
public class ForgotPasswordController {

    private UserForgetPasswordService userForgetPasswordService;
    private UserForgotPasswordDto userForgotPasswordDto;

    @GetMapping
    public String getForgotPasswordForm(){
        return "forgot-password-page";
    }
    @PostMapping
    public String enterPassword(@RequestBody UserForgotPasswordDto userForgotPasswordDto){
        return userForgetPasswordService.sendToken(userForgotPasswordDto);
    }


    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return userForgetPasswordService.confirmToken(token);
    }

}
