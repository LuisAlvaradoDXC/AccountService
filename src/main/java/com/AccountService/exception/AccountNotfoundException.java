package com.AccountService.exception;

public class AccountNotfoundException extends GlobalException {

    //////

    protected static final long serialVersionUID = 2L;

    //////

    public AccountNotfoundException() {
        super("No hay cuentas, la lista está vacía.");
    }

    public AccountNotfoundException(Long accountId) {
        super("Account with id: " + accountId + " not found");
    }

}
