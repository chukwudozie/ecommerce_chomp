package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.model.token.ConfirmationToken;
import com.chompfooddeliveryapp.repository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token) {

        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }

    public Optional<ConfirmationToken> getTokenByUserEmail(String email){
        return confirmationTokenRepository.findByUser_Email(email);
    }

}
