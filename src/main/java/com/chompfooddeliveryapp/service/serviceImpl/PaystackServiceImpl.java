package com.chompfooddeliveryapp.service.serviceImpl;


import com.chompfooddeliveryapp.dto.PayStackRequestDto;
import com.chompfooddeliveryapp.dto.PayStackResponseDto;
import com.chompfooddeliveryapp.dto.VerifyTransactionDto;
import com.chompfooddeliveryapp.model.enums.PaymentMethod;
import com.chompfooddeliveryapp.model.enums.TransactionType;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
/*@RequiredArgsConstructor*/
public class PaystackServiceImpl {
    private final TransactionServiceImpl transactionService;
    private final UserRepository userRepository;
    private final WebClient.Builder webClient;
    private final ObjectMapper objectMapper;
    private final String secret;

    public PaystackServiceImpl(TransactionServiceImpl transactionService, UserRepository userRepository,
                               WebClient.Builder webClient, ObjectMapper objectMapper,@Value("${paystack.Secret}") String secret) {
        this.transactionService = transactionService;
        this.userRepository = userRepository;
        this.webClient = webClient;
        this.objectMapper = objectMapper;
        this.secret = secret;
    }


    public Object initializePaystackTransaction (PayStackRequestDto payStackRequestDto,
                                                 long userId, TransactionType type ){
        String transactionReference = transactionService.getTransactionRefence(userId, type, PaymentMethod.PAYSTACK);

        if (!(transactionReference.contains("CHOMPPSTK") || transactionReference.contains("CHOMPFND"))){
            return new ResponseEntity<>(transactionReference, HttpStatus.BAD_REQUEST );
        }

        User user = userRepository.findById(userId).get();
        String[] channels = {"card", "bank"};
        payStackRequestDto.setAmount(payStackRequestDto.getAmount()*100);
        payStackRequestDto.setReference(transactionReference);
        payStackRequestDto.setEmail(user.getEmail());
        payStackRequestDto.setChannels(channels);

        return webClient.build().post().uri("https://api.paystack.co/transaction/initialize").
                header("Authorization", "Bearer " + secret ).bodyValue(payStackRequestDto)
                .retrieve().bodyToMono(Object.class).block();
    }

    public PayStackResponseDto verifyPaystackTransaction(VerifyTransactionDto verifyTransactionDto) throws JsonProcessingException {
        String paystackObject =  webClient.build().get().
                uri("https://api.paystack.co/transaction/verify/" + verifyTransactionDto.getTransactionReference()).
                header("Authorization", "Bearer " + secret)
                .retrieve().bodyToMono(String.class).block();

        PayStackResponseDto payStackDto = objectMapper.readValue( paystackObject, PayStackResponseDto.class);


        return payStackDto;
    }




}
