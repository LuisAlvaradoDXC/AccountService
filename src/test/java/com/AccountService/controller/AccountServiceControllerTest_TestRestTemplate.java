package com.AccountService.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("prod")
public class AccountServiceControllerTest_TestRestTemplate {

    //////

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    //////

    @Test
    void givenValidBalance_returnsValidStatus() throws Exception {

        long idAccount = 1L;
        int amount = 100;
        long ownerId = 1L;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/cuentas/addMoney/" + idAccount + "/" + amount + "/" + ownerId,
                HttpMethod.PUT,
                entity,
                String.class
        );

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);

    }

    @Test
    void givenValidBalance_returnsNotValidStatus() throws Exception {

        long idAccount = 1L;
        int amount = -100;
        long ownerId = 1L;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/cuentas/addMoney/" + idAccount + "/" + amount + "/" + ownerId,
                HttpMethod.PUT,
                entity,
                String.class
        );

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);

    }

    @Test
    void givenValidOwnerId_deleteAccountSuccessFull() throws Exception {

        long ownerId = 1L;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/cuentas/owner/" + ownerId,
                HttpMethod.DELETE,
                entity,
                String.class
        );

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    void givenNotValidOwnerId_deleteAccountSuccessFull() throws Exception {

        long ownerId = -1L;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/cuentas/owner/" + ownerId,
                HttpMethod.DELETE,
                entity,
                String.class
        );

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    //////

}
