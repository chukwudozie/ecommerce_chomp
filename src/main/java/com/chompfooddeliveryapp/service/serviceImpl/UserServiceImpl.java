package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.dto.SignupDto;
import com.chompfooddeliveryapp.dto.UserDto;
import com.chompfooddeliveryapp.model.enums.UserGender;
import com.chompfooddeliveryapp.model.enums.UserRole;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.payload.JwtResponse;
import com.chompfooddeliveryapp.payload.MessageResponse;
import com.chompfooddeliveryapp.repository.UserRepository;
import com.chompfooddeliveryapp.security.jwt.JwtUtils;
import com.chompfooddeliveryapp.service.serviceInterfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserServiceImpl implements UserServiceInterface {

    private final JwtUtils utils;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(JwtUtils utils, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UserRepository userRepository, PasswordEncoder encoder) {
        this.utils = utils;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }


    @Override
    public ResponseEntity<MessageResponse> createUser(SignupDto signupDto) {
        if (userRepository.existsByEmail(signupDto.getEmail())) {
            return ResponseEntity.ok(new MessageResponse("User already exists!"));
        }
        User user = new User(signupDto.getEmail(),
                signupDto.getFirstName(), signupDto.getLastName(),
                encoder.encode(signupDto.getPassword()));


        UserRole role = signupDto.getRoles();
        System.out.println(role);

        user.setUserRole(role);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @Override
    public ResponseEntity<?> loginUser (@RequestBody UserDto loginRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

            User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found with Email: " + loginRequest.getEmail()));

            UserRole roles = user.getUserRole();

            String jwt = utils.generateJwtToken(authentication);
            return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), user.getId(), roles));

        } catch (BadCredentialsException e) {
            throw new Exception("incorrect username or passoword!");
        }
    }

}
