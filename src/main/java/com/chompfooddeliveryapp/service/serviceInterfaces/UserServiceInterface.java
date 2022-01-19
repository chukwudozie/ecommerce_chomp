package com.chompfooddeliveryapp.service.serviceInterfaces;

import com.chompfooddeliveryapp.dto.SignupDto;
import com.chompfooddeliveryapp.payload.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserServiceInterface {

    public ResponseEntity<MessageResponse> createUser(SignupDto signupDto) throws Exception;
}
