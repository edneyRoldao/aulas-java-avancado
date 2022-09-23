package com.edntisolutions.bankproject.exceptions;

public class AddressNotFoundException extends RuntimeException {

    public AddressNotFoundException(String message) {
        super(message);
    }

}
