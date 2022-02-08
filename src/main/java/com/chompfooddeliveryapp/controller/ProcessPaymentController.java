package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.service.serviceInterfaces.Paymentservice;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessPaymentController {

    private Paymentservice paymentservice;
}
