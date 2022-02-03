package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.controller.WithdrawalController;
import com.chompfooddeliveryapp.dto.WithdrawalDto;
import com.chompfooddeliveryapp.dto.WithdrawalRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WithdrawalControllerTest {

    @Mock
    WalletWithdrawServiceImpl walletWithdrawServiceImpl;

    @InjectMocks
    WithdrawalController withdrawalController;

    @Test
    public void test_walletWithdrawal(){
        WithdrawalRequest withdrawalRequest = new WithdrawalRequest();
        WithdrawalDto withdrawalDto = new WithdrawalDto();
        when(walletWithdrawServiceImpl.walletWithdraw(withdrawalRequest)).thenReturn(new ResponseEntity<>(withdrawalDto, HttpStatus.OK));
        ResponseEntity<WithdrawalDto> response = withdrawalController.walletWithdrawal(withdrawalRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(withdrawalDto, response.getBody());
    }

}
