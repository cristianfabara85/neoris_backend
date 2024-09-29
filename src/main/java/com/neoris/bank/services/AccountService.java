package com.neoris.bank.services;


import com.neoris.bank.models.Account;

import java.text.ParseException;
import java.util.List;

public interface AccountService {

    Account createAccount(Account account) throws ParseException;

    Account deleteAccount(Long accountNumber);

    List<Account> findAllAccounts();

    List<Account> findAccountsByDocumentId(String documentId);

    Account updateAccount(Long accountId, Account account);
}
