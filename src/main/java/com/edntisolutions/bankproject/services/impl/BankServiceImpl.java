package com.edntisolutions.bankproject.services.impl;

import com.edntisolutions.bankproject.client.ViacepClient;
import com.edntisolutions.bankproject.exceptions.AccountAlreadyExistException;
import com.edntisolutions.bankproject.exceptions.AccountValidationException;
import com.edntisolutions.bankproject.exceptions.AddressNotFoundException;
import com.edntisolutions.bankproject.models.Account;
import com.edntisolutions.bankproject.models.Address;
import com.edntisolutions.bankproject.models.Client;
import com.edntisolutions.bankproject.repositories.AccountRepository;
import com.edntisolutions.bankproject.repositories.AddressRepository;
import com.edntisolutions.bankproject.repositories.ClientRepository;
import com.edntisolutions.bankproject.request.AccountRequest;
import com.edntisolutions.bankproject.response.AddressResponse;
import com.edntisolutions.bankproject.services.BankService;
import com.edntisolutions.bankproject.utils.AppUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.edntisolutions.bankproject.utils.DateUtil.stringToLocalDate;
import static com.edntisolutions.bankproject.utils.NumberUtil.generateRandomNumber;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

    private final ViacepClient viacepClient;
    private final AddressRepository addressRepository;
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;

    @Override
    public Account createAccount(AccountRequest request) {
        log.info("BankServiceImpl.createAccount init - request={}", request);

        accountRepository
                .findFirstByClientDocumentOrClientEmail(request.getDocument(), request.getEmail())
                .ifPresent(account -> {
                    String baseErrorMessage = "A conta já existe para o cpf ou email informado. o numero dela é: ";
                    throw new AccountAlreadyExistException(baseErrorMessage + account.getNumber());
                });

        AddressResponse addressResponse = getAddress(request)
                .orElseThrow(() -> new AddressNotFoundException("Endereco nao encontrado"));

        if (!AppUtil.isCpfValid(request.getDocument()))
            throw new AccountValidationException("cpf inválido");

        Address address = Address
                .builder()
                .city(addressResponse.getLocalidade())
                .state(addressResponse.getUf())
                .address(addressResponse.getLogradouro())
                .number(request.getNumber())
                .cep(request.getCep())
                .secondAddress(request.getSecondAddress())
                .build();

        Address savedAddress = addressRepository.save(address);

        Client client = Client
                .builder()
                .address(savedAddress)
                .name(request.getName())
                .document(request.getDocument())
                .phone(Long.parseLong(request.getPhone()))
                .email(request.getEmail())
                .birthdate(stringToLocalDate(request.getBirthdate(), "dd/MM/yyyy"))
                .build();

        Client savedClient = clientRepository.save(client);

        Account account = Account
                .builder()
                .number(generateAccountNumber())
                .client(savedClient)
                .registration(LocalDateTime.now())
                .password(Long.parseLong(request.getPassword()))
                .build();

        Account savedAccount = accountRepository.save(account);

        log.info("BankServiceImpl.createAccount end -  account={}", savedAccount);

        return account;
    }

    @Override
    public void deposit(Long accountNumber, BigDecimal value) {
        // todo - buscar a conta no banco
        Account account = accountRepository
                .findFirstByNumber(accountNumber)
                .orElseThrow(() -> new AccountValidationException("Conta não localizada"));

        // todo - verificar se a conta está ativa
        if (nonNull(account.getDeactivation()))
            throw new AccountValidationException("Conta informada está inativa");

        // todo - add valor
        BigDecimal current = nonNull(account.getBalance()) ? account.getBalance() : new BigDecimal(0);
        BigDecimal total = current.add(value);
        account.setBalance(total);

        // todo - salvar alteracao
        accountRepository.save(account);
    }

    @Override
    public void withdraw(Long accountNumber, BigDecimal value) {
        // todo - limite de saque por dia
        // todo - validar a senha do cabra antes de saquar

        // todo - buscar a conta no banco
        Account account = accountRepository
                .findFirstByNumber(accountNumber)
                .orElseThrow(() -> new AccountValidationException("Conta não localizada"));

        // todo - verificar se a conta está ativa
        if (nonNull(account.getDeactivation()))
            throw new AccountValidationException("Conta informada está inativa");

        // todo - pegando o valor do objeto
        BigDecimal current = nonNull(account.getBalance()) ? account.getBalance() : new BigDecimal(0);

        // todo - verificar se tem saldo
        if (current.compareTo(value) < 0)
            throw new AccountValidationException("Saldo insuficiente");

        // todo - subtrair
        BigDecimal newValue = current.subtract(value);
        account.setBalance(newValue);

        // todo - salvar alteracao
        accountRepository.save(account);
    }

    private Long generateAccountNumber() {
        Long number = generateRandomNumber();

        while (accountRepository.findFirstByNumber(number).isPresent()) {
            number = generateRandomNumber();
        }

        return number;
    }

    private Optional<AddressResponse> getAddress(AccountRequest request) {
        try {
            AddressResponse addressResponse = viacepClient.getAddressByCep(request.getCep());

            if (addressResponse.isErro()) {
                return Optional.empty();
            }

            return Optional.of(addressResponse);

        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
