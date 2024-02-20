package com.AccountService.controller;

import com.AccountService.model.Account;
import com.AccountService.servicio.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RestController
@RequestMapping({"/cuentas", ""})
@Tag(name = "Accounts API", description = "Accounts management APIs")
@Validated
public class AccountController {
    Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private IAccountService accountService;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(accountService.getAccounts(), HttpStatus.OK);
    }

    @PostMapping("/create")
    @Operation(summary = "Para crear una cuenta nueva", description = "Agrega una cuenta al repositorio de datos recibiendo los parametro typo,balance y ownerID, la fecha y el ID se colocaran automaticamente")
    public ResponseEntity<?> createAccount(@RequestBody @Valid Account account) {
        return new ResponseEntity<>(accountService.create(account), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Para solicitar una cuenta mediante su 'ID'", description = "Mediante el el ID de la cuenta recurrirá a un service, el cual recupera la 'cuenta' de un repositorio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuando hay cuentas a devolver."),
            @ApiResponse(responseCode = "404", description = "Cuando no hay cuentas a devolver.")
    })
    public ResponseEntity<?> getOne(@PathVariable @NotBlank @Parameter(name = "ID", description = "Indica un ID por el que filtrar", example = "1,2,3,4") Long id) {
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
    @Operation(summary = "Para agregar balance a una cuenta ", description = "el servicio sumara la cantidad recogida en el controlador a la cantidad consultada del repositorio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Cuando se ha agregado dinero correctamente"),
            @ApiResponse(responseCode = "404", description = "Cuando no se ha encontrado la cuenta."),
            @ApiResponse(responseCode = "400", description = "los datos introducidos no son validos")
    })
    public ResponseEntity<?> addBalance(@PathVariable @NotNull @Parameter(name = "idAccount", description = "Indica el id de la cuenta", example = "1,2,3,4") Long idAccount,
                                        @NotNull @Min(0) @PathVariable @Parameter(name = "amount", description = "Indica una cantidad a sumar", example = "100,500,4000") int amount,
                                        @NotNull @PathVariable @Parameter(name = "ownerId", description = "Indica un ID perteneciente al propietario de la cuenta", example = "1,2,3,4") Long ownerId) {
        return new ResponseEntity<>(accountService.addBalance(idAccount, amount, ownerId), HttpStatus.ACCEPTED);
    }

    @PutMapping("/withdrawMoney/{idAccount}/{amount}/{ownerId}")
    public ResponseEntity<?> withdrawMoney(@PathVariable Long idAccount, @PathVariable int amount, @PathVariable Long ownerId) {
        return new ResponseEntity<>(accountService.withdrawBalance(idAccount, amount, ownerId), HttpStatus.ACCEPTED);
    }

}

