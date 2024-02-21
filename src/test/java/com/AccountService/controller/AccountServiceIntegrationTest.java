package com.AccountService.controller;

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
import org.springframework.boot.Banner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Date;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class AccountServiceIntegrationTest {

    //////

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountServiceImpl accountService;

    //////

    @Test
    void givenValidBalance_returnsValidStatus() throws Exception {

        long idAccount = 1L;
        int amount = 500;
        long ownerId = 1L;
        String endPoint = "/cuentas/addMoney/" + idAccount + "/" + amount + "/" + ownerId;

        Mockito.when( accountService.addBalance(idAccount, amount, ownerId) ).thenReturn(new Account());
        mockMvc.perform( MockMvcRequestBuilders.put(endPoint) ).andExpect( status().isAccepted() );

    }

    @Test
    void givenValidBalance_returnsNotValidStatus() throws Exception {

        long idAccount = 1L;
        int amount = -500;
        long ownerId = 1L;
        String endPoint = "/cuentas/addMoney/" + idAccount + "/" + amount + "/" + ownerId;

        Mockito.when( accountService.addBalance(idAccount, amount, ownerId) ).thenReturn(new Account());
        mockMvc.perform( MockMvcRequestBuilders.put(endPoint) ).andExpect( status().isPreconditionFailed() );

    }

    @Test
    void givenValidOwnerId_deleteAccountSuccessFull() throws Exception {

        long ownerId = 1L;
        String endPoint = "";

        //mockMvc.perform(  )

    }

    @Test
    void givenNotValidOwnerId_deleteAccountSuccessFull() throws Exception {

    }

    //////

}
