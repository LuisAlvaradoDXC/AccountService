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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Date;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class AccountServiceIntegrationTest {

    @TestConfiguration
    static class AccountServiceIntegrationTestConfiguration {

        @Bean
        public IAccountService accountService() {
            return new AccountServiceImpl();
        }

        @Bean
        public AccountController accountController() {
            return new AccountController();
        }

    }

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private AccountController accountController;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private EntityManager entityManager;

    @MockBean
    private EntityManagerFactory entityManagerFactory;

    @BeforeEach
    public void setUp() {
        Mockito.when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);

        Account cuenta = new Account(1L, "fake-account", new Date(), 200, 1L, null);
        Mockito.when(accountRepository.findById(1L))
                .thenReturn(Optional.of(cuenta));

        Mockito.when(accountService.addBalance(1L, 200, 1L))
                .thenReturn(cuenta);
    }


    @Test
    void givenValidBalance_returnsValidStatus() throws Exception {
        Long idAccount = 1L;
        int amount = 100;
        Long ownerId = 1L;

        mockMvc.perform(put("/cuentas/addMoney/{idAccount}/{amount}/{ownerId}", idAccount, amount, ownerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void givenValidBalance_returnsNotValidStatus() throws Exception {

    }

    @Test
    void givenValidOwnerId_deleteAccountSuccessfull() throws Exception {

    }

    @Test
    void givenNotValidOwnerId_deleteAccountSuccessfull() throws Exception {

    }


}
