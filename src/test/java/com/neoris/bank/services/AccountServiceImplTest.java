package com.neoris.bank.services;

import com.neoris.bank.exceptions.ResourceNotFoundException;
import com.neoris.bank.models.Account;
import com.neoris.bank.models.Client;
import com.neoris.bank.models.Movement;
import com.neoris.bank.models.Person;
import com.neoris.bank.repository.AccountRepository;
import com.neoris.bank.repository.ClientRepository;
import com.neoris.bank.repository.MovementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private MovementRepository movementRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Account account;
    private Client client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        client = new Client();
        client.setId(1L);
        client.setPerson(new Person());

        account = new Account();
        account.setId(1L);
        account.setNumber(123L);
        account.setClient(client);
        account.setInitialBalance(BigDecimal.valueOf(1000));
    }

    @Test
    void testCreateAccountWhenAccountExists() throws ParseException {
        when(accountRepository.findAccountByNumber(anyLong())).thenReturn(Optional.of(account));

        Account result = accountService.createAccount(account);

        assertNotNull(result);
        assertEquals(account.getNumber(), result.getNumber());
    }

    @Test
    void testCreateAccountWhenAccountDoesNotExist() throws ParseException {
        when(accountRepository.findAccountByNumber(anyLong())).thenReturn(Optional.empty());
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account result = accountService.createAccount(account);

        assertNotNull(result);
        assertEquals(account.getNumber(), result.getNumber());

    }

    @Test
    void testDeleteAccountWhenAccountExistsWithMovements() {
        account.setMovements(List.of(new Movement()));
        when(accountRepository.findAccountByNumber(anyLong())).thenReturn(Optional.of(account));

        Account result = accountService.deleteAccount(123L);

        assertNotNull(result);
        assertEquals(14, result.getState());

    }

    @Test
    void testDeleteAccountWhenAccountDoesNotExist() {
        when(accountRepository.findAccountByNumber(anyLong())).thenReturn(Optional.empty());

        Account result = accountService.deleteAccount(123L);

        assertNotNull(result);
        assertNull(result.getId());
    }

    @Test
    void testFindAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);

        when(accountRepository.findAll()).thenReturn(accounts);

        List<Account> result = accountService.findAllAccounts();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testFindAccountsByDocumentIdWhenExists() {
        List<Account> accounts = List.of(account);
        when(accountRepository.findAccountsByDocumentId(anyString())).thenReturn(Optional.of(accounts));

        List<Account> result = accountService.findAccountsByDocumentId("123456789");

        assertNotNull(result);
        assertEquals(1, result.size());
      }

    @Test
    void testFindAccountsByDocumentIdWhenDoesNotExist() {
        when(accountRepository.findAccountsByDocumentId(anyString())).thenReturn(Optional.empty());

        List<Account> result = accountService.findAccountsByDocumentId("123456789");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testUpdateAccountWhenAccountExists() {
        when(accountRepository.findAccountByNumber(anyLong())).thenReturn(Optional.of(account));

        Account updatedAccount = new Account();
        updatedAccount.setState(10);
        updatedAccount.setType(1);

        Account result = accountService.updateAccount(123L, updatedAccount);

        assertNotNull(result);
        assertEquals(10, result.getState());
        assertEquals(1, result.getType());
    }

    @Test
    void testUpdateAccountWhenAccountDoesNotExist() {
        when(accountRepository.findAccountByNumber(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class,
                () -> accountService.updateAccount(123L, account),
                "Expected updateAccount to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("No existe una cuenta con else número"));
    }
}
