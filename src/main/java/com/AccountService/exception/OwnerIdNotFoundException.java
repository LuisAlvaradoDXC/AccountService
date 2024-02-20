package com.AccountService.exception;

public class OwnerIdNotFoundException extends GlobalException {


    public OwnerIdNotFoundException(Long ownerId) {
        super("Owner with id: " + ownerId + " not found");
    }

    public OwnerIdNotFoundException() {
        super("ID no valido");
    }
}
