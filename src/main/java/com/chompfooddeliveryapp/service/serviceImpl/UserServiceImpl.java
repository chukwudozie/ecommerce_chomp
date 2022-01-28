package com.chompfooddeliveryapp.service.serviceImpl;


import com.chompfooddeliveryapp.Mail.MailService;

import com.chompfooddeliveryapp.dto.ChangePasswordDto;
import com.chompfooddeliveryapp.dto.EditUserDetailsDto;

import com.chompfooddeliveryapp.dto.SignupDto;
import com.chompfooddeliveryapp.dto.UserDto;
import com.chompfooddeliveryapp.dto.token.ConfirmationToken;
import com.chompfooddeliveryapp.dto.token.ConfirmationTokenService;
import com.chompfooddeliveryapp.model.enums.UserRole;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.payload.JwtResponse;
import com.chompfooddeliveryapp.payload.MessageResponse;
import com.chompfooddeliveryapp.repository.UserRepository;
import com.chompfooddeliveryapp.security.jwt.JwtUtils;
import com.chompfooddeliveryapp.service.serviceInterfaces.UserServiceInterface;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
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


import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;
import java.util.Optional;
//@AllArgsConstructor
//@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserServiceInterface {

    private final JwtUtils utils;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final MailService mailService;

    @Autowired
    public UserServiceImpl(JwtUtils utils, AuthenticationManager authenticationManager,
                           UserDetailsService userDetailsService, UserRepository userRepository,
                           PasswordEncoder encoder, ConfirmationTokenService confirmationTokenService, MailService mailService) {
        this.utils = utils;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.confirmationTokenService = confirmationTokenService;
        this.mailService = mailService;
    }

    @Override
    public ResponseEntity<MessageResponse> createUser(SignupDto signupDto) {
        if (userRepository.existsByEmail(signupDto.getEmail().toLowerCase(Locale.ROOT))) {
            return ResponseEntity.ok(new MessageResponse("User already exists!", null,null,null));
        }
        User user = new User(signupDto.getEmail(),
                signupDto.getFirstName(), signupDto.getLastName(),
                encoder.encode(signupDto.getPassword()));


        UserRole role = signupDto.getRoles();
        System.out.println(role);

        user.setUserRole(role);
        userRepository.save(user);

        // TODO: Send confirmation token
        String token = UUID.randomUUID().toString();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiresAt = createdAt.plusHours(24) ;
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(24),
                user
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);
        String link = "http://localhost:808/confirm?token=" + token;
        String content = "<p>Hello,</p>"
                + "<p>Please verify your email with the link below.</p>"
                + "<p>Click the link below activate your account:</p>"
                + "<p><a href=\"" + link + "\">Verify Email</a></p>"
                + "<br>"
                + "<p> Ignore this email if you have verified your email, "
                + "or you have not made the request.</p>";

        try {
            mailService.sendMail(signupDto.getEmail(), content, "Here is your confirmation email. ");
        } catch (MailjetException | MailjetSocketTimeoutException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(new MessageResponse("Complete your registration with the token", token, createdAt, expiresAt));
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if(expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userRepository.enableAppUser(confirmationToken.getUser().getEmail());

        return "Confirmed!!";

    }

    @Override
    public ResponseEntity<?> loginUser(@RequestBody UserDto loginRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

            User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found with Email: " + loginRequest.getEmail()));

            UserRole roles = user.getUserRole();

            String jwt = utils.generateJwtToken(authentication);
            if(user.getEnabled()) {
                return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), user.getId(), roles));
            }else {
                return ResponseEntity.badRequest().body("Email has not been verified");
            }

        } catch (BadCredentialsException e) {
            throw new Exception("incorrect username or password!");
        }
    }

    @Override
    public void changePassword(ChangePasswordDto changePasswordDto) {
        Optional<User> currentUser = userRepository.getUserByPassword(changePasswordDto.getOldPassword());
        String newPassword = changePasswordDto.getNewPassword();
        String confirmPassword = changePasswordDto.getConfirmPassword();
        if (currentUser.isPresent() && newPassword.equals(confirmPassword)) {
            currentUser.get().setPassword(newPassword);
            userRepository.save(currentUser.get());

        }
    }

    @Override
    public void updateUser(EditUserDetailsDto editUserDetailsDto) {
        Optional<User> loggedInUser = userRepository.findByEmail(editUserDetailsDto.getEmail());
        if (loggedInUser.isPresent()) {
            loggedInUser.get().setFirstName(editUserDetailsDto.getFirstname());
            loggedInUser.get().setLastName(editUserDetailsDto.getLastname());
            loggedInUser.get().setEmail(editUserDetailsDto.getEmail());
            loggedInUser.get().setUserGender(editUserDetailsDto.getGender());
            loggedInUser.get().setDob(editUserDetailsDto.getDateOfBirth());
            userRepository.save(loggedInUser.get());
        }
    }
}