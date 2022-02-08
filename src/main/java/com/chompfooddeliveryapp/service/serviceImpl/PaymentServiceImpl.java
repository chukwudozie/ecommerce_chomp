package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.dto.PayStackRequestDto;
import com.chompfooddeliveryapp.dto.ProcessPaymentRequest;
import com.chompfooddeliveryapp.model.enums.PaymentMethod;
import com.chompfooddeliveryapp.model.enums.TransactionType;
import com.chompfooddeliveryapp.service.serviceInterfaces.Paymentservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements Paymentservice {
    private final PaystackServiceImpl paystackService;
    private final WalletWithdrawServiceImpl withdrawService;
    private final OrderServiceImplementation orderService;

    @Autowired
    public PaymentServiceImpl(PaystackServiceImpl paystackService, WalletWithdrawServiceImpl withdrawService,
       OrderServiceImplementation orderService) {

        this.paystackService = paystackService;
        this.withdrawService = withdrawService;
        this.orderService = orderService;
    }

    //todo: Makera has refused to convert the Object type to Paystack custom class
    public Object processPayment(ProcessPaymentRequest request, Long userId /**paymentDTO to contain the amount to be paid and the payment method**/)
    {

        //todo: Check the incoming request to see if the user wants to pay by card or wallet
        if (request.getPaymentMethod().equals(PaymentMethod.PAYSTACK.name())){
            //todo: if the user selects paystack call the paystack service
            //todo: call the initialize paystack
            PayStackRequestDto requestDto = new PayStackRequestDto();
            requestDto.setAmount(request.getAmount());
          return   paystackService.initializePaystackTransaction(requestDto,userId, TransactionType.DEBIT);
            //todo: If payment is successful, Use the order service to set the status of the order to confirmed
            //todo: Makera will add callback URL in front end to redirect user to verification and order confirmation

        }

        //todo: check if the payment method from the dto is for paystack by comparing it with the dto
        if(request.getPaymentMethod().equals(PaymentMethod.EWALLET.name())){

            //todo: if the user selects wallet transaction, call the withdraw from wallet
            //todo: If payment is successful, Use the order service to set the status of the order to confirmed

        }

        //todo: implement the necessary deductions from the corresponding account(or check the methods for that)
        return null;
    }
}
