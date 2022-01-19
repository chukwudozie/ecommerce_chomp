package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.dto.SignupDto;
import com.chompfooddeliveryapp.dto.UserDto;
import com.chompfooddeliveryapp.model.enums.UserRole;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.payload.JwtResponse;
//import com.chompfooddeliveryapp.repository.RoleRepository;
import com.chompfooddeliveryapp.repository.UserRepository;
import com.chompfooddeliveryapp.security.jwt.JwtUtils;
import com.chompfooddeliveryapp.service.serviceImpl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@Slf4j
@RequestMapping("/auth")
public class AuthController {
    private final JwtUtils utils;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public AuthController(JwtUtils utils, UserRepository userRepository, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UserServiceImpl userServiceImpl) {
        this.utils = utils;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupDto signupDto) throws Exception {
        log.info("I got into signup");

        return userServiceImpl.createUser(signupDto);

    }

        @PostMapping("/login")
        public ResponseEntity<?> loginUser (@RequestBody UserDto loginRequest) throws Exception {
            try {
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

                final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

                User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found with Email: "+ loginRequest.getEmail()));

                UserRole roles = user.getUserRole();

                String jwt = utils.generateJwtToken(authentication);
                return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), user.getId(), roles));

            } catch (BadCredentialsException e) {
                throw new Exception("incorrect username or passoword!");
            }

    }

}
