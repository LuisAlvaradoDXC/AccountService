package com.AccountService.controller;

import com.AccountService.exception.OwnerIdNotFoundException;
import com.AccountService.model.Account;
import com.AccountService.persistence.AccountRepository;
import com.AccountService.servicio.AccountServiceImpl;
import com.AccountService.servicio.IAccountService;
import org.apache.coyote.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
//@WebMvcTest
//@AutoConfigureMockMvc
public class AccountServiceIntegrationTest {

    @TestConfiguration
    static class AccountControllerTestContextConfiguration {

        @Bean
        public AccountController accountController() {
            return new AccountController();
        }
    }

    @Autowired
    private AccountController accountController;

    @MockBean
    private IAccountService accountService;

/*    @Autowired
    private MockMvc mockMvc;*/


    @BeforeEach
    public void setup() {
        when(accountService.addBalance(anyLong(), anyInt(), anyLong()))
                .thenAnswer(invocation -> {
                    // Simular el comportamiento del servicio
                    Long id = invocation.getArgument(0);
                    int amount = invocation.getArgument(1);
                    Long ownerId = invocation.getArgument(2);

                    Account account = new Account(id, "TestAccount", new Date(), 100, ownerId, null);
                    int newBalance = account.getBalance() + amount;
                    account.setBalance(newBalance);
                    return account;
                });
       Mockito.doThrow(OwnerIdNotFoundException.class).when(accountService).delete(25L);

    }

    @Test
    void givenValidBalance_returnsValidStatus() throws Exception {
        Long accountId = 1L;
        int amount = 50;
        Long ownerId = 1L;

        ResponseEntity<?> result = accountController.addBalance(accountId, amount, ownerId);

        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
    }

    @Test
    void givenValidBalance_returnsNotValidStatus() throws Exception {
        Long accountId = 1L;
        int amount = -50;
        Long ownerId = 1L;

        // ResponseEntity<?> result = accountController.addBalance(accountId, amount, ownerId);
/*        mockMvc.perform(put("/cuentas/addMoney/{accountId}/{amount}/{ownerId}", accountId, amount, ownerId))
                .andExpect(status().isPreconditionFailed());*/
        //  assertEquals(HttpStatus.PRECONDITION_FAILED, result.getStatusCode());

 /*           mockMvc.perform(put("/cuentas/addMoney/" + accountId + "/" + amount + "/" + ownerId)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isPreconditionFailed());*/
    }

    @Test
    void givenValidOwnerId_deleteAccountSuccessfull() throws Exception {
        Long ownerId = 1L;
        ResponseEntity<?> result = accountController.deleteAccountByOwnerId(ownerId);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    void givenNotValidOwnerId_deleteAccountSuccessfull() throws Exception {

        Long accountId = 25L;
        ResponseEntity<?> result = accountController.deleteAccount(accountId);

         assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

    }

    /*mockMvc.perform(put("/cuentas/addMoney/" + accountId + "/" + amount + "/" + ownerId)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isPreconditionFailed());*/

}
