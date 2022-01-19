package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.dto.SignupDto;
import com.chompfooddeliveryapp.model.enums.UserRole;
import com.chompfooddeliveryapp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder encoder;

    UserServiceImpl userService;

    private TestEntityManager entityManager;

    @BeforeEach

    void setUp() {
        userService = new UserServiceImpl(userRepository, encoder);
    }

    @Test
    public void testCreateUserThrowsAnException(){

        SignupDto signupDto = new SignupDto();
        signupDto.setEmail("blah@gmail.com");
        signupDto.setPassword("hshjsfjhsfhjs");
        signupDto.setFirstName("MunaMuna");
        signupDto.setLastName("OnyeOnye");
        signupDto.setRoles(UserRole.ADMIN);

        when(userRepository.existsByEmail(any())).thenReturn(true);
        Assertions.assertThrows(Exception.class, () -> userService.createUser(signupDto));
        verifyNoMoreInteractions(userRepository);
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