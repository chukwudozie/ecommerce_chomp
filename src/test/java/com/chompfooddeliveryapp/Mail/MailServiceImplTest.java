package com.chompfooddeliveryapp.Mail;

import com.chompfooddeliveryapp.service.serviceImpl.EmailSenderService;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class MailServiceImplTest {

//    @Test
//    void sendMail() throws MailjetException, MailjetSocketTimeoutException {
//        String email = "ahangba.marve@decagon.dev";
//        String text = "This is the it";
//        String title = "Mail Verification";
//
//        EmailSenderService mailService;
//        mailService = mock(EmailSenderService.class);
//        doNothing().when(mailService).sendMail(email, text, title);
//        mailService.sendMail(email, text, title);
//        verify(mailService, times(1)).sendMail(email, text, title);
//    }
}