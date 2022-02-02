package com.chompfooddeliveryapp.forgotpassword.dto;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.chompfooddeliveryapp.forgotpassword.email.EmailSender;
import com.chompfooddeliveryapp.forgotpassword.token.ConfirmationToken;
import com.chompfooddeliveryapp.forgotpassword.token.ConfirmationTokenService;
import com.chompfooddeliveryapp.model.enums.UserGender;
import com.chompfooddeliveryapp.model.enums.UserRole;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.repository.UserRepository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {UserForgetPasswordService.class, BCryptPasswordEncoder.class})
@ExtendWith(SpringExtension.class)
class UserForgetPasswordServiceTest {
    @MockBean
    private ConfirmationTokenService confirmationTokenService;

    @MockBean
    private EmailSender emailSender;

    @MockBean
    private User user;

    @Autowired
    private UserForgetPasswordService userForgetPasswordService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testSendToken() {
        User user = new User();
        user.setDob(mock(Date.class));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setSubscribed(true);
        user.setUserGender(UserGender.MALE);
        user.setUserRole(UserRole.USER);
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findByEmail((String) any())).thenReturn(ofResult);
        doNothing().when(this.emailSender).send((String) any(), (String) any());
        doNothing().when(this.confirmationTokenService)
                .saveConfirmationToken((com.chompfooddeliveryapp.forgotpassword.token.ConfirmationToken) any());
        this.userForgetPasswordService.sendToken(new UserForgotPasswordDto("Jane", "Doe", "jane.doe@example.org"));
        verify(this.userRepository).findByEmail((String) any());
        verify(this.emailSender).send((String) any(), (String) any());
        verify(this.confirmationTokenService)
                .saveConfirmationToken((com.chompfooddeliveryapp.forgotpassword.token.ConfirmationToken) any());
    }

    @Test
    void testSendToken2() {
        User user = new User();
        user.setDob(mock(Date.class));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setSubscribed(true);
        user.setUserGender(UserGender.MALE);
        user.setUserRole(UserRole.USER);
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findByEmail((String) any())).thenReturn(ofResult);
        doNothing().when(this.emailSender).send((String) any(), (String) any());
        doThrow(new IllegalStateException("foo")).when(this.confirmationTokenService)
                .saveConfirmationToken((com.chompfooddeliveryapp.forgotpassword.token.ConfirmationToken) any());
        assertThrows(IllegalStateException.class, () -> this.userForgetPasswordService
                .sendToken(new UserForgotPasswordDto("Jane", "Doe", "jane.doe@example.org")));
        verify(this.userRepository).findByEmail((String) any());
        verify(this.emailSender).send((String) any(), (String) any());
        verify(this.confirmationTokenService)
                .saveConfirmationToken((com.chompfooddeliveryapp.forgotpassword.token.ConfirmationToken) any());
    }

    @Test
    void testSendToken3() {
        when(this.userRepository.findByEmail((String) any())).thenReturn(Optional.empty());
        doNothing().when(this.emailSender).send((String) any(), (String) any());
        doNothing().when(this.confirmationTokenService)
                .saveConfirmationToken((com.chompfooddeliveryapp.forgotpassword.token.ConfirmationToken) any());
        assertThrows(IllegalStateException.class, () -> this.userForgetPasswordService
                .sendToken(new UserForgotPasswordDto("Jane", "Doe", "jane.doe@example.org")));
        verify(this.userRepository).findByEmail((String) any());
    }

    @Test
    void testSendToken4() {
        User user = new User();
        user.setDob(mock(Date.class));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setSubscribed(true);
        user.setUserGender(UserGender.MALE);
        user.setUserRole(UserRole.USER);
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findByEmail((String) any())).thenReturn(ofResult);
        doNothing().when(this.emailSender).send((String) any(), (String) any());
        doNothing().when(this.confirmationTokenService)
                .saveConfirmationToken((com.chompfooddeliveryapp.forgotpassword.token.ConfirmationToken) any());
        UserForgotPasswordDto userForgotPasswordDto = mock(UserForgotPasswordDto.class);
        when(userForgotPasswordDto.getFirst_name()).thenThrow(new IllegalStateException("foo"));
        when(userForgotPasswordDto.getEmail()).thenReturn("jane.doe@example.org");
        assertThrows(IllegalStateException.class, () -> this.userForgetPasswordService.sendToken(userForgotPasswordDto));
        verify(this.userRepository).findByEmail((String) any());
        verify(userForgotPasswordDto, atLeast(1)).getEmail();
        verify(userForgotPasswordDto).getFirst_name();
    }

    @Test
    void testConfirmToken() {
        User user = new User();
        user.setDob(mock(Date.class));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setSubscribed(true);
        user.setUserGender(UserGender.MALE);
        user.setUserRole(UserRole.USER);

        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken.setId(123L);
        confirmationToken.setToken("ABC123");
        confirmationToken.setUser(user);
        Optional<ConfirmationToken> ofResult = Optional.of(confirmationToken);
        when(this.confirmationTokenService.getToken((String) any())).thenReturn(ofResult);
        assertThrows(IllegalStateException.class, () -> this.userForgetPasswordService.confirmToken("ABC123"));
        verify(this.confirmationTokenService).getToken((String) any());
    }

    @Test
    void testConfirmToken2() {
        when(this.confirmationTokenService.getToken((String) any())).thenThrow(new IllegalStateException("foo"));
        assertThrows(IllegalStateException.class, () -> this.userForgetPasswordService.confirmToken("ABC123"));
        verify(this.confirmationTokenService).getToken((String) any());
    }

    @Test
    void testConfirmToken3() {
        User user = new User();
        user.setDob(mock(Date.class));
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setSubscribed(true);
        user.setUserGender(UserGender.MALE);
        user.setUserRole(UserRole.USER);

        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setConfirmedAt(null);
        confirmationToken.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
        confirmationToken.setId(123L);
        confirmationToken.setToken("ABC123");
        confirmationToken.setUser(user);
        Optional<ConfirmationToken> ofResult = Optional.of(confirmationToken);
        when(this.confirmationTokenService.getToken((String) any())).thenReturn(ofResult);
        assertThrows(IllegalStateException.class, () -> this.userForgetPasswordService.confirmToken("ABC123"));
        verify(this.confirmationTokenService).getToken((String) any());
    }

    @Test
    void testConfirmToken4() {
        when(this.confirmationTokenService.getToken((String) any())).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> this.userForgetPasswordService.confirmToken("ABC123"));
        verify(this.confirmationTokenService).getToken((String) any());
    }
}

