package com.chompfooddeliveryapp.forgotpassword.token;

import com.chompfooddeliveryapp.forgotpassword.token.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenServiceInt {

    public void saveConfirmationToken(ConfirmationToken token);
    public Optional<ConfirmationToken> getToken(String token);
    public int setConfirmedAt(String token);
}
