package com.neoris.bank.services;

import com.neoris.bank.exceptions.ResourceNotFoundException;
import com.neoris.bank.models.Account;
import com.neoris.bank.models.Client;
import com.neoris.bank.models.Movement;
import com.neoris.bank.models.Person;
import com.neoris.bank.repository.AccountRepository;
import com.neoris.bank.repository.ClientRepository;
import com.neoris.bank.repository.MovementRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MovementRepository movementRepository;


    @Override
    public Account createAccount(Account account) throws ParseException {
        Optional<Account> cuenta = accountRepository.findAccountByNumber(account.getNumber());
        if (cuenta.isPresent()) {
            Account accountUpdate = cuenta.get();
            mapAccountToUpdate(account, accountUpdate);
            accountRepository.save(accountUpdate);
            return accountUpdate;
        } else {
            Account account1 = accountRepository.save(account);
            movementRepository.save(generateInitialMovement(account1));
            return account;
        }
    }

    private Movement generateInitialMovement(Account account) throws ParseException {
        Movement mov = new Movement();
        mov.setType(15);
        mov.setAccountId(account.getId());
        if (account.getInitialBalance() == null) {
            mov.setAmount(BigDecimal.ZERO);
        } else {
            mov.setAmount(account.getInitialBalance());
        }
        mov.setAmount(account.getInitialBalance());
        mov.setTransactionDate(Instant.now());
        mov.setBalance(BigDecimal.ZERO.add(mov.getAmount()).setScale(4, RoundingMode.HALF_UP));
        return mov;
    }

    @Override
    public Account deleteAccount(Long accountNumber) {
        Optional<Account> cuenta = accountRepository.findAccountByNumber(accountNumber);
        Account cuentaUpdate;
        if (cuenta.isPresent() && !cuenta.get().getMovements().isEmpty()) {
            cuentaUpdate = cuenta.get();
            cuentaUpdate.setState(14);
            accountRepository.save(cuentaUpdate);
        } else {
            cuentaUpdate = new Account();
        }

        return cuentaUpdate;
    }

    @Override
    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public List<Account> findAccountsByDocumentId(String documentId) {
        if(accountRepository.findAccountsByDocumentId(documentId).isPresent()){
            return accountRepository.findAccountsByDocumentId(documentId).get().stream().peek(account -> {
                account.getClient().setPerson((Person) Hibernate.unproxy(account.getClient().getPerson()));
            }).collect(Collectors.toList());
        }else{
            return new ArrayList<Account>();
        }

    }

    @Override
    public Account updateAccount(Long accountNumber, Account account) {
        Optional<Account> cuenta = Optional.ofNullable(accountRepository.findAccountByNumber(accountNumber).orElseThrow(() -> new ResourceNotFoundException("No existe una cuenta con else número")));
        Account cuentaUpdate;
        if (cuenta.isPresent()) {
            cuentaUpdate = cuenta.get();
            cuentaUpdate.setState(account.getState());
            cuentaUpdate.setType(account.getType());

            accountRepository.save(cuentaUpdate);
        } else {
            cuentaUpdate = new Account();
        }

        return cuentaUpdate;
    }


    private void mapAccountToUpdate(Account account, Account accountToUpdate) {
        accountToUpdate.setState(account.getState());
        accountToUpdate.setNumber(account.getNumber());
        accountToUpdate.setType(account.getType());
        accountToUpdate.setClientId(account.getClient().getId());
        accountToUpdate.setInitialBalance(account.getInitialBalance());
        accountToUpdate.setClient((Client) Hibernate.unproxy(accountToUpdate.getClient()));
        accountToUpdate.getClient().setPerson(account.getClient().getPerson());

    }
}
