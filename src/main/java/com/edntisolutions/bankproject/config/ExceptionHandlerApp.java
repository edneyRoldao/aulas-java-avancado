package com.edntisolutions.bankproject.config;

import com.edntisolutions.bankproject.exceptions.AccountAlreadyExistException;
import com.edntisolutions.bankproject.exceptions.AccountValidationException;
import com.edntisolutions.bankproject.exceptions.AddressNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerApp {

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<String> handle(AddressNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(AccountAlreadyExistException.class)
    public ResponseEntity<String> handle(AccountAlreadyExistException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(AccountValidationException.class)
    public ResponseEntity<String> handle(AccountValidationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
