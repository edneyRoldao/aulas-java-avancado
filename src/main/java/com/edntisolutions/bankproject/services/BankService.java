package com.edntisolutions.bankproject.services;

import com.edntisolutions.bankproject.models.Account;
import com.edntisolutions.bankproject.request.AccountRequest;

import java.math.BigDecimal;

public interface BankService {

    Account createAccount(AccountRequest accountRequest);

    void deposit(Long accountNumber, BigDecimal value);

    void withdraw(Long accountNumber, BigDecimal value);

}
