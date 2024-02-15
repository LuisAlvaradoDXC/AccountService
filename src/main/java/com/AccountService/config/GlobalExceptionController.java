package com.AccountService.config;

import com.AccountService.exception.AccountNotfoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {

        @ExceptionHandler(AccountNotfoundException.class)
        ResponseEntity<Object> noSuchElementExceptionHandler(AccountNotfoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Excepcion de cuenta!");
    }
}
