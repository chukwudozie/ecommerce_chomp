package com.chompfooddeliveryapp.service.serviceInterfaces;

import com.chompfooddeliveryapp.dto.ChangePasswordDto;
import com.chompfooddeliveryapp.dto.EditUserDetailsDto;
import com.chompfooddeliveryapp.dto.SignupDto;
import com.chompfooddeliveryapp.dto.UserDto;
import com.chompfooddeliveryapp.payload.MessageResponse;
import com.chompfooddeliveryapp.payload.UpdatePayLoad;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;


public interface UserServiceInterface {

    public ResponseEntity<MessageResponse> createUser(SignupDto signupDto);

    public ResponseEntity<?> loginUser (@RequestBody UserDto loginRequest, HttpServletResponse response) throws Exception;

    UpdatePayLoad changePassword(ChangePasswordDto changePasswordDto, Long id);

    void updateUser(EditUserDetailsDto editUserDetailsDto, Long id);

    ResponseEntity<?> resendToVerifyEmail(UserDto loginRequest);

    Long getUserIDFromSecurityContext();

    ResponseEntity<?> confirmToken(String token);
}
