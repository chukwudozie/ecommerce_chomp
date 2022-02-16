//package com.chompfooddeliveryapp.service.serviceImpl;
//
//import com.chompfooddeliveryapp.dto.PayStackRequestDto;
//import com.chompfooddeliveryapp.dto.PayStackResponseDto;
//import com.chompfooddeliveryapp.model.enums.PaymentMethod;
//import com.chompfooddeliveryapp.model.enums.TransactionType;
//import com.chompfooddeliveryapp.model.enums.UserGender;
//import com.chompfooddeliveryapp.model.users.User;
//import com.chompfooddeliveryapp.repository.TransactionRepository;
//import com.chompfooddeliveryapp.repository.UserRepository;
//import com.chompfooddeliveryapp.repository.WalletRepository;
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import okhttp3.mockwebserver.MockResponse;
//import okhttp3.mockwebserver.MockWebServer;
//import okhttp3.mockwebserver.RecordedRequest;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.reactive.function.client.WebClient;
//
//
//import java.io.IOException;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
////@ExtendWith(MockitoExtension.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class PaystackServiceImplTest {
//
//
//
//    @Mock
//    UserRepository userRepository;
//    @Mock
//    TransactionRepository transactionRepository;
//    @Mock
//    WalletRepository walletRepository;
//    @Mock
//    TransactionServiceImpl transactionService;
//    @Mock
//    String transactionReference;
//    @Mock
//    WebClient.Builder webClient;
////    @Mock
//    @Value("${paystack.Secret}")
//    String secret;
//
//    @InjectMocks
//    PaystackServiceImpl paystackService;
//    ObjectMapper objectMapper;
//    User user;
//    public static MockWebServer mockBackEnd;
//    PayStackRequestDto payStackRequestDto;
//    String [] channels = {"card", "bank"};
////    String transactionReference;
//
//
//    @BeforeAll
//    void setUp() throws IOException {
//        mockBackEnd = new MockWebServer();
//        mockBackEnd.start();
//        payStackRequestDto = new PayStackRequestDto();
//        transactionService = new TransactionServiceImpl(walletRepository, userRepository, transactionRepository);
//        webClient = WebClient.builder();
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
//        payStackRequestDto.setAmount(100*100);
//        payStackRequestDto.setEmail(user.getEmail());
//        payStackRequestDto.setChannels(channels);
////        transactionReference = transactionService.getTransactionRefence(user.getId(),
////                TransactionType.CREDIT, PaymentMethod.PAYSTACK);
////        payStackRequestDto.setReference(transactionReference);
//
//    }
//
//    @AfterAll
//    static void tearDown() throws IOException {
//        mockBackEnd.shutdown();
//    }
//
//    @BeforeEach
//    void initialize() {
//
//      var result =   webClient.build().post().uri("https://api.paystack.co/transaction/initialize").
//                header("Authorization", "Bearer " + "sk_test_2d5c43445c023f91a8f13334df77580390a395c9" ).bodyValue(payStackRequestDto)
//                .retrieve().bodyToMono(Object.class).block();
////        paystackService.initializePaystackTransaction(payStackRequestDto, user.getId(), TransactionType.CREDIT);
////        String baseUrl = String.format("http://localhost:%s",
////                mockBackEnd.getPort());
//    }
//
//
//    @Test
//    void initializePaystackTransaction() throws InterruptedException, JsonProcessingException {
//        Object object = new Object();
//        objectMapper = new ObjectMapper();
//
//        mockBackEnd.enqueue(new MockResponse()
//                .setBody(objectMapper.writeValueAsString(object))
//                .addHeader("Content-Type", "application/json")
//        );
//
//        RecordedRequest recordedRequest = mockBackEnd.takeRequest();
//        assertEquals("POST", recordedRequest.getMethod());
//        assertEquals("https://api.paystack.co/transaction/initialize", recordedRequest.getPath());
//////
////            mockBackEnd.enqueue(new MockReq
////            );
//    }
//
//    @Test
//    void verifyPaystackTransaction() {
//    }
//}