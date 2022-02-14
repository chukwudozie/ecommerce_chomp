package com.chompfooddeliveryapp.service.serviceImpl;


import com.chompfooddeliveryapp.Mail.MailService;

import com.chompfooddeliveryapp.dto.ChangePasswordDto;
import com.chompfooddeliveryapp.dto.EditUserDetailsDto;

import com.chompfooddeliveryapp.dto.SignupDto;
import com.chompfooddeliveryapp.dto.UserDto;
import com.chompfooddeliveryapp.dto.token.ConfirmationToken;
import com.chompfooddeliveryapp.dto.token.ConfirmationTokenService;

import com.chompfooddeliveryapp.security.PasswordValidator;
import com.chompfooddeliveryapp.service.serviceInterfaces.CartService;

import com.chompfooddeliveryapp.exception.BadRequestException;
import com.chompfooddeliveryapp.model.carts.Cart;
import com.chompfooddeliveryapp.repository.CartRepository;
import com.chompfooddeliveryapp.service.serviceInterfaces.CartService;
import com.chompfooddeliveryapp.model.enums.UserRole;
import com.chompfooddeliveryapp.model.users.Role;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.model.wallets.Wallet;
import com.chompfooddeliveryapp.payload.JwtResponse;
import com.chompfooddeliveryapp.payload.MessageResponse;
import com.chompfooddeliveryapp.repository.RoleRepository;
import com.chompfooddeliveryapp.repository.UserRepository;
import com.chompfooddeliveryapp.repository.WalletRepository;
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


import javax.servlet.http.HttpServletResponse;
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
    private final RoleRepository roleRepository;
    private final CartService cartService;
    private final PasswordEncoder encoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final MailService mailService;
    private final WalletRepository walletRepository;
    private final PasswordValidator passwordValidator;

    @Autowired
    private final WalletServiceImpl walletService;

    @Autowired
    public UserServiceImpl(JwtUtils utils, AuthenticationManager authenticationManager,
                           UserDetailsService userDetailsService, UserRepository userRepository,
                           PasswordEncoder encoder, ConfirmationTokenService confirmationTokenService,
                           MailService mailService, WalletRepository walletRepository, WalletServiceImpl walletService,
                           RoleRepository roleRepository,CartService cartService, PasswordValidator passwordValidator) {
        this.utils = utils;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.encoder = encoder;
        this.confirmationTokenService = confirmationTokenService;
        this.mailService = mailService;
        this.walletRepository = walletRepository;
        this.walletService = walletService;
        this.roleRepository = roleRepository;
        this.passwordValidator=passwordValidator;

    }

    @Override
    public ResponseEntity<MessageResponse> createUser(SignupDto signupDto) {
        if (userRepository.existsByEmail(signupDto.getEmail().toLowerCase(Locale.ROOT))) {
            return ResponseEntity.ok(new MessageResponse("User already exists!", null,null,null));
        }
        boolean passwordValue = passwordValidator.validate(signupDto.getPassword());
        if (!passwordValue){

            return ResponseEntity.ok(new MessageResponse("Invalid password!", null,null,null));
        }
        User user = new User(signupDto.getEmail(),
                signupDto.getFirstName(), signupDto.getLastName(),
                encoder.encode(signupDto.getPassword()));

        Role role = roleRepository.findByName(UserRole.USER).get();
        user.setRole(role);



    //addng a wallet to a user by team D
        Wallet wallet = new Wallet();
        Wallet savedWallet = walletRepository.save(wallet);
        user.setWalletId(savedWallet);
    //adding wallet ends here



        userRepository.save(user);
        // add cart to user saved with USER Role
        cartService.createCartForUser(user);
        // TODO: Send confirmation token
        return ResponseEntity.ok(getResponseEntity(user, signupDto.getEmail()));
    }



    @Transactional
    public ResponseEntity<?> confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));
        if (confirmationToken.getConfirmedAt() != null) {
            System.out.println("Already confirmed");
            throw new BadRequestException("email already confirmed");
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if(expiredAt.isBefore(LocalDateTime.now())) {
            System.out.println("expired");
           return ResponseEntity.ok(getResponseEntity(confirmationToken.getUser(), confirmationToken.getUser().getEmail()));
        }
        confirmationTokenService.setConfirmedAt(token);
        userRepository.enableAppUser(confirmationToken.getUser().getEmail());

        return ResponseEntity.ok("Registration verified");

    }

    @Override
    public ResponseEntity<?> loginUser(@RequestBody UserDto loginRequest, HttpServletResponse response) throws Exception {
        try {
            System.out.println(" I am here");
            Optional<ConfirmationToken> userConfirmation = confirmationTokenService.getTokenByUserEmail(loginRequest.getEmail());
            if(userConfirmation.isPresent() && userConfirmation.get().getConfirmedAt() == null){
                System.out.println("No token");
                throw new BadRequestException("Please confirm your token before login");

            }
            System.out.println("Cant pass here");
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

            User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found with Email: " + loginRequest.getEmail()));

            UserRole roles = user.getRole().getName();

            String jwt = utils.generateJwtToken(authentication);
            System.out.println(jwt);
            System.out.println(authentication);
            if(user.getEnabled()) {
                response.addHeader("Authorization", "Bearer " + jwt);
                return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), user.getId(), roles));
            }else {
                return ResponseEntity.badRequest().body("Email has not been verified");
            }

        } catch (BadCredentialsException e) {
            throw new Exception("incorrect username or password!");
        }
    }

    @Override
    public void changePassword(ChangePasswordDto changePasswordDto, Long id) {

        User currentUser = userRepository.findUserById(id).orElseThrow(
        ()-> new BadRequestException("User Not Found")

        );
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                currentUser.getEmail(), changePasswordDto.getOldPassword())
        );

        String newPassword = changePasswordDto.getNewPassword();
        String confirmPassword = changePasswordDto.getConfirmPassword();

        if (newPassword.equals(confirmPassword)) {
            currentUser.setPassword(encoder.encode(newPassword));
            userRepository.save(currentUser);
        } else {
            throw new BadRequestException("Incorrect password");

        }
    }

    @Override
    public void updateUser(EditUserDetailsDto editUserDetailsDto, Long id) {
        Optional<User> loggedInUser = userRepository.findUserById(id);
        if (loggedInUser.isPresent()) {
            loggedInUser.get().setFirstName(editUserDetailsDto.getFirstname());
            loggedInUser.get().setLastName(editUserDetailsDto.getLastname());
            loggedInUser.get().setEmail(editUserDetailsDto.getEmail());
            loggedInUser.get().setUserGender(editUserDetailsDto.getGender());
            loggedInUser.get().setDob(editUserDetailsDto.getDateOfBirth());
            userRepository.save(loggedInUser.get());
            System.out.println("User updated "+loggedInUser.get().getEmail());
        }
    }

    @Override
    public ResponseEntity<?> resendToVerifyEmail(UserDto loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).get();
        return ResponseEntity.ok(getResponseEntity(user, loginRequest.getEmail()));
    }

    private MessageResponse getResponseEntity(User user, String email) {
        String token = UUID.randomUUID().toString();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiresAt = createdAt.plusMinutes(2) ;
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                createdAt,
                expiresAt,
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
            mailService.sendMail(email, content, "Here is your confirmation email. ");
        } catch (MailjetException | MailjetSocketTimeoutException e) {
            e.printStackTrace();
        }
        return new MessageResponse("Complete your registration with the token", token, createdAt, expiresAt);
    }
}
