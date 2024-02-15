package com.AccountService.controller;

import com.AccountService.model.Account;
import com.AccountService.servicio.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController

@RequestMapping({"/cuentas", ""})
public class AccountController {
    Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private IAccountService accountService;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(accountService.getAccounts(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody @Valid Account account) {
        return new ResponseEntity<>(accountService.create(account), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(accountService.getAccount(id), HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable Long id, @RequestBody @Valid Account account) {
        return new ResponseEntity<>(accountService.updateAccount(id, account), HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/owner/{ownerId}")
    public ResponseEntity<?> deleteAccountByOwnerId(@PathVariable("ownerId") Long id) {
        accountService.deleteAccountsUsingOwnerId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/addMoney/{idAccount}/{amount}/{ownerId}")
    public ResponseEntity<?> addBalance(@PathVariable Long idAccount, @PathVariable int amount, @PathVariable Long ownerId) {
        return new ResponseEntity<>(accountService.addBalance(idAccount, amount, ownerId), HttpStatus.ACCEPTED);
    }

    @PutMapping("/withdrawMoney/{idAccount}/{amount}/{ownerId}")
    public ResponseEntity<?> withdrawMoney(@PathVariable Long idAccount, @PathVariable int amount, @PathVariable Long ownerId) {
        return new ResponseEntity<>(accountService.withdrawBalance(idAccount, amount, ownerId), HttpStatus.ACCEPTED);
    }

}

