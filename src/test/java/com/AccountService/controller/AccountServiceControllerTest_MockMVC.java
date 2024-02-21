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

import java.nio.charset.StandardCharsets;

import static net.bytebuddy.matcher.ElementMatchers.is;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// WebEnviroments configura nuestro entorno en tiempo de ejecucion y el .MOCK simulara el entorno de servlet
//habilita la configuracion de MockMVC
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class AccountServiceControllerTest_MockMVC {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    IAccountService accountService;
    @Autowired
    private MockMvc mock;

    @Test
    void givenValidBalance_returnsValidStatus() throws Exception {

        Long idAccount = 1L;
        int amount = 100;
        Long ownerId = 1L;

        mock.perform(put("/cuentas/addMoney/{idAccount}/{amount}/{ownerId}", idAccount, amount, ownerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void givenValidBalance_returnsNotValidStatus() throws Exception {

        Long idAccount = 1L;
        int amount = -100;
        Long ownerId = 1L;

        mock.perform(put("/cuentas/addMoney/{idAccount}/{amount}/{ownerId}", idAccount, amount, ownerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE + ";charset=" + StandardCharsets.UTF_8.name()));
    }

    @Test
    void givenValidOwnerId_deleteAccountSuccessfull() throws Exception {
        Long ownerId = 1L;

        mock.perform(delete("/cuentas/owner/{ownerId}", ownerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void givenNotValidOwnerId_deleteAccountSuccessfull() throws Exception {
        Long ownerId = -1L;

        mock.perform(delete("/cuentas/owner/{ownerId}", ownerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}
