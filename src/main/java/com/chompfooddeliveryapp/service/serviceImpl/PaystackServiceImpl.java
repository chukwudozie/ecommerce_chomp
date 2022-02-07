package com.chompfooddeliveryapp.service.serviceImpl;


import com.chompfooddeliveryapp.dto.PayStackRequestDto;
import com.chompfooddeliveryapp.model.enums.PaymentMethod;
import com.chompfooddeliveryapp.model.enums.TransactionType;
import com.chompfooddeliveryapp.model.users.User;
import com.chompfooddeliveryapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PaystackServiceImpl {
    private final TransactionServiceImpl transactionService;
    private final UserRepository userRepository;
    private final WebClient.Builder webClient;

    @Value("${chompfood.app.paystackSecret}")
    private String secret;

    public PaystackServiceImpl(TransactionServiceImpl transactionService, UserRepository userRepository, WebClient.Builder webClient) {
        this.transactionService = transactionService;
        this.userRepository = userRepository;
        this.webClient = webClient;
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




}
