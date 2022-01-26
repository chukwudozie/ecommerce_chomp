package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.dto.SignupDto;
import com.chompfooddeliveryapp.model.enums.UserRole;
import com.chompfooddeliveryapp.repository.UserRepository;
import com.chompfooddeliveryapp.security.jwt.JwtUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder encoder;
    @Mock
    JwtUtils utils;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    UserDetailsService userDetailsService;



    UserServiceImpl userService;

    private TestEntityManager entityManager;

//    @BeforeEach
//
//    void setUp() {
//        userService = new UserServiceImpl(utils, authenticationManager, userDetailsService, userRepository, encoder);
//    }

    @Test
    public void testCreateUserIsFalse(){

        SignupDto signupDto = new SignupDto();
        signupDto.setEmail("blah@gmail.com");
        signupDto.setPassword("hshjsfjhsfhjs");
        signupDto.setFirstName("MunaMuna");
        signupDto.setLastName("OnyeOnye");
        signupDto.setRoles(UserRole.ADMIN);

        when(userRepository.existsByEmail(any())).thenReturn(true);
        userService.createUser(signupDto);
        verify(userRepository, times(0)).save(any());
    }

    @Test
    public void testCreateUserIsSuccessful() throws Exception {

        SignupDto signupDto = new SignupDto();
        signupDto.setEmail("blah@gmail.com");
        signupDto.setPassword("hshjsfjhsfhjs");
        signupDto.setFirstName("MunaMuna");
        signupDto.setLastName("OnyeOnye");
        signupDto.setRoles(UserRole.ADMIN);

        when(userRepository.existsByEmail(any())).thenReturn(false);
        userService.createUser(signupDto);
        verify(userRepository, times(1)).save(any());
    }
}