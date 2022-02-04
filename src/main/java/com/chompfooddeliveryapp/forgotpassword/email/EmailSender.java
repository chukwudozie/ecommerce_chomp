package com.chompfooddeliveryapp.forgotpassword.email;

public interface EmailSender {
    void send(String to, String email);
}
