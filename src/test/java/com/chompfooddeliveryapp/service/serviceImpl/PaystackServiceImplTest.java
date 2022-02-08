//package com.chompfooddeliveryapp.service.serviceImpl;
//
//import com.chompfooddeliveryapp.model.enums.UserGender;
//import com.chompfooddeliveryapp.model.users.User;
//import com.chompfooddeliveryapp.repository.UserRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import static org.mockito.Mockito.*;
//import org.mockito.junit.jupiter.MockitoExtension;
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//
//import java.util.Optional;
//
//
//class PaystackServiceImplTest {
//
//
//    @Mock
//    UserRepository userRepository;
//
//    @InjectMocks
//    PaystackServiceImpl paystackService;
//    ObjectMapper objectMapper;
//    User user;
//
//    @BeforeEach
//    void setUp(){
//        user = new User();
//        user.setId(2L);
//        user.setFirstName("mark");
//        user.setLastName("Apiri");
//        user.setEmail("makera@gmail.com");
//        user.setPassword("123456789");
//        user.setDob(null);
//        user.setEnabled(true);
//        user.setUserGender(UserGender.MALE);
//        user.setSubscribed(true);
//    }
//
//    @Test
//    void initializePaystackTransaction() {
//        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
//    }
//
//    @Test
//    void verifyPaystackTransaction() {
//    }
//}