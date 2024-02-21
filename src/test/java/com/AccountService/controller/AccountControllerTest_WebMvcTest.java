package com.AccountService.controller;

import com.AccountService.exception.OwnerIdNotFoundException;
import com.AccountService.model.Account;
import com.AccountService.persistence.AccountRepository;
import com.AccountService.servicio.AccountServiceImpl;
import com.AccountService.servicio.IAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest_WebMvcTest {

    /*Mockeamos la persistencia y el service falseando datos del service*/
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAccountService accountService;
    @MockBean
    private AccountRepository accountRepository;

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
         doThrow(OwnerIdNotFoundException.class).when(accountService).deleteAccountsUsingOwnerId(25L);
    }

    @Test
    void givenValidBalance_returnsValidStatus() throws Exception {
        // Realizar la solicitud PUT al endpoint /addMoney
        mockMvc.perform(put("/addMoney/1/500/1"))
                .andExpect(status().isAccepted());
    }

    @Test
    void givenNotValidBalance_returnsValidStatus() throws Exception {
        // Realizar la solicitud PUT al endpoint /addMoney
        mockMvc.perform(put("/addMoney/1/-500/1"))
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    void givenValidOwnerId_deleteAccountSuccessfull() throws Exception {
        mockMvc.perform(delete("/cuentas/owner/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void givenNotValidOwnerId_deleteAccountSuccessfull() throws Exception {
        mockMvc.perform(delete("/cuentas/owner/25"))
                .andExpect(status().isNotFound());
    }

}