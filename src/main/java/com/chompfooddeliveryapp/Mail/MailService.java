package com.chompfooddeliveryapp.Mail;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import org.springframework.stereotype.Service;

@Service
public interface MailService {
    void sendMail(String email, String text, String title) throws MailjetException, MailjetSocketTimeoutException;
}
