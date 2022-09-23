package com.edntisolutions.bankproject.api;

import com.edntisolutions.bankproject.models.Account;
import com.edntisolutions.bankproject.request.AccountRequest;
import com.edntisolutions.bankproject.services.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Slf4j
@RestController
public class BankController {

    @Autowired
    private BankService bankService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("api/create-account")
    public Account createAccount(@RequestBody AccountRequest request) {
        log.info("BankController.createAccount init");

        Account account = bankService.createAccount(request);

        log.info("BankController.createAccount end");

        return account;
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("api/deposit/{accountNumber}")
    public String deposit(@RequestParam("value") BigDecimal value,
                          @PathVariable("accountNumber") Long number) {
        log.info("BankController.deposit init");

        bankService.deposit(number, value);

        log.info("BankController.deposit end");
        return "Deposito realizado com sucesso";
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("api/withdraw/{accountNumber}")
    public String withdraw(@RequestParam("value") BigDecimal value,
                           @PathVariable("accountNumber") Long number) {
        log.info("BankController.withdraw init");

        bankService.withdraw(number, value);

        log.info("BankController.withdraw end");
        return "Saque realizado com sucesso";
    }

}
