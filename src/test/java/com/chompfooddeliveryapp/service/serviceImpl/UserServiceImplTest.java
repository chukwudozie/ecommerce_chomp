package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.dto.ChangePasswordDto;
import com.chompfooddeliveryapp.dto.EditUserDetailsDto;
import com.chompfooddeliveryapp.dto.SignupDto;
import com.chompfooddeliveryapp.model.enums.UserGender;
import com.chompfooddeliveryapp.model.enums.UserRole;
import com.chompfooddeliveryapp.model.users.User;
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

import java.sql.Date;
import java.util.Optional;

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

    @BeforeEach

    void setUp() {
        userService = new UserServiceImpl(utils, authenticationManager, userDetailsService, userRepository, encoder);
    }

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


    @Test
    public void testChangePassword(){
        ChangePasswordDto changePasswordDto = new ChangePasswordDto("one", "two", "two");
        userService = mock(UserServiceImpl.class);
        doNothing().when(userService).changePassword(any());
        userService.changePassword(changePasswordDto);
        verify(userService, times(1)).changePassword(changePasswordDto);
    }


    @Test
    public void testUpdateUser(){

        EditUserDetailsDto editUserDetailsDto = new EditUserDetailsDto(
                "Amara", "Ojiakor", "amara@gmail.com",
                UserGender.FEMALE, new Date(2000-12-11)
        );
        userService = mock(UserServiceImpl.class);
        doNothing().when(userService).updateUser(any());
        userService.updateUser(editUserDetailsDto);
        verify(userService, times(1)).updateUser(editUserDetailsDto);

    }

}