package com.edntisolutions.bankproject.exceptions;

public class AccountAlreadyExistException extends RuntimeException{

    public AccountAlreadyExistException(String m) {
        super(m);
    }

}
