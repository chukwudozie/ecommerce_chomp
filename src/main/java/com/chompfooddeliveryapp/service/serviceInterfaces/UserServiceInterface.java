package com.chompfooddeliveryapp.service.serviceInterfaces;

import com.chompfooddeliveryapp.dto.SignupDto;
import com.chompfooddeliveryapp.dto.UserDto;
import com.chompfooddeliveryapp.payload.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public interface UserServiceInterface {

    public ResponseEntity<MessageResponse> createUser(SignupDto signupDto);

    public ResponseEntity<?> loginUser (@RequestBody UserDto loginRequest) throws Exception;
}
