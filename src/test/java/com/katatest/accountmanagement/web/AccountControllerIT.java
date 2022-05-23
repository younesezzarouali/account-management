package com.katatest.accountmanagement.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.katatest.accountmanagement.domain.enumeration.DepositType;
import com.katatest.accountmanagement.domain.Amount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Test
    void should_return_true_when_user_deposit_money() throws Exception {
        final Amount amount = new Amount();
        amount.setValue(1000.5d);
        amount.setType(DepositType.DEFERRED);

        mockMvc.perform(post("/api/account/1/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content((new ObjectMapper()).writeValueAsString(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void should_return_false_when_user_deposit_money_account_not_found() throws Exception {
        final Amount amount = new Amount();
        amount.setValue(1000.5d);
        amount.setType(DepositType.DEFERRED);

        mockMvc.perform(post("/api/account/-1/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content((new ObjectMapper()).writeValueAsString(amount)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorKey").value("accountNotFound"));
    }

    @Test
    void should_return_true_when_user_withdraw_money() throws Exception {
        final Amount amount = new Amount();
        amount.setValue(1000.5d);
        amount.setType(DepositType.DEFERRED);

        mockMvc.perform(post("/api/account/1/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content((new ObjectMapper()).writeValueAsString(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void should_return_false_when_user_withdraw_money_account_not_found() throws Exception {
        final Amount amount = new Amount();
        amount.setValue(12000d);
        amount.setType(DepositType.INSTANTANEOUS);

        mockMvc.perform(post("/api/account/1/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content((new ObjectMapper()).writeValueAsString(amount)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorKey").value("insufficientBalance"));
    }

    @Test
    void should_return_false_when_user_withdraw_money_insufficient_balance() throws Exception {
        final Amount amount = new Amount();
        amount.setValue(12000d);
        amount.setType(DepositType.INSTANTANEOUS);

        mockMvc.perform(post("/api/account/-1/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content((new ObjectMapper()).writeValueAsString(amount)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorKey").value("accountNotFound"));
    }

    @Test
    void should_print_account_statement() throws Exception {
        mockMvc.perform(get("/api/account/1/print"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Account statement : \n" +
                        "ID : 1001\n" +
                        "Title : null\n" +
                        "Amount : 11000.0\n" +
                        "Balance : 11000.0\n" +
                        "Creation Date : 2017-05-22"));
    }
}