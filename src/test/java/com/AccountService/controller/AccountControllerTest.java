package com.AccountService.controller;

import com.AccountService.persistence.AccountRepository;
import com.AccountService.servicio.IAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// WebEnviroments configura nuestro entorno en tiempo de ejecucion y el .MOCK simulara el entorno de servlet
//habilita la configuracion de MockMVC
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class AccountControllerTest {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    IAccountService accountService;
    @Autowired
    private MockMvc mock;
    @Test
    void givenValidBalance_returnsValidStatus() throws Exception{
         Long id = 1L;
        int amount = 100;
        Long ownerId = 1L;

        String endPoint = "cuentas/addMoney/" + id + "/" + amount + "/" + ownerId;

        mock.perform(get(endPoint).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE));
    }
    }
