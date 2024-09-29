package com.neoris.bank.controllers;


import com.google.gson.Gson;
import com.neoris.bank.models.Account;
import com.neoris.bank.models.Response;
import com.neoris.bank.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    private Gson gson = new Gson();

    private Account account;
    private Response response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        account = new Account();
        account.setId(1L);
        response = new Response();
        accountController.setGson(gson);
    }

    @Test
    void testCreateAccount_Success() throws Exception {
        when(accountService.createAccount(any(Account.class))).thenReturn(account);

        Response actualResponse = accountController.createAccount(account);
        assertNotNull(actualResponse);
        assertEquals(200, actualResponse.getCode());
        assertEquals("Se ha creado/actualizado la siguiente cuenta:", actualResponse.getMessage());

        verify(accountService, times(1)).createAccount(any(Account.class));
    }

    @Test
    void testCreateAccount_Failure() throws Exception {
        when(accountService.createAccount(any(Account.class))).thenReturn(new Account());

        Response actualResponse = accountController.createAccount(account);
        assertNotNull(actualResponse);
        assertEquals(200, actualResponse.getCode());
        assertEquals("No se ha creado ninguna cuenta.", actualResponse.getMessage());

        verify(accountService, times(1)).createAccount(any(Account.class));
    }

    @Test
    void testUpdateAccount_Success() throws Exception {
        when(accountService.updateAccount(anyLong(), any(Account.class))).thenReturn(account);

        Response actualResponse = accountController.updateAccount(1L, account);
        assertNotNull(actualResponse);
        assertEquals(200, actualResponse.getCode());
        assertEquals("Se ha actualizado la siguiente cuenta:", actualResponse.getMessage());

        verify(accountService, times(1)).updateAccount(anyLong(), any(Account.class));
    }

    @Test
    void testUpdateAccount_Failure() throws Exception {
        when(accountService.updateAccount(anyLong(), any(Account.class))).thenReturn(new Account());

        Response actualResponse = accountController.updateAccount(1L, account);
        assertNotNull(actualResponse);
        assertEquals(200, actualResponse.getCode());
        assertEquals("No se ha actualizado ninguna cuenta.", actualResponse.getMessage());

        verify(accountService, times(1)).updateAccount(anyLong(), any(Account.class));
    }

    @Test
    void testDeleteAccount_Success() throws Exception {
        when(accountService.deleteAccount(anyLong())).thenReturn(account);

        Response actualResponse = accountController.deleteAccount(1L);
        assertNotNull(actualResponse);
        assertEquals(200, actualResponse.getCode());
        assertEquals("cuenta eliminada correctamente", actualResponse.getMessage());

        verify(accountService, times(1)).deleteAccount(anyLong());
    }

    @Test
    void testDeleteAccount_Failure() throws Exception {
        when(accountService.deleteAccount(anyLong())).thenReturn(new Account());

        Response actualResponse = accountController.deleteAccount(1L);
        assertNotNull(actualResponse);
        assertEquals(200, actualResponse.getCode());
        assertEquals("No se ha eliminado ninguna cuenta.", actualResponse.getMessage());

        verify(accountService, times(1)).deleteAccount(anyLong());
    }

    @Test
    void testFindAllAccounts_Success() throws Exception {
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        when(accountService.findAllAccounts()).thenReturn(accounts);

        Response actualResponse = accountController.findAllAccounts();
        assertNotNull(actualResponse);
        assertEquals(200, actualResponse.getCode());
        assertEquals("Cuentas encontradas:", actualResponse.getMessage());

        verify(accountService, times(1)).findAllAccounts();
    }

    @Test
    void testFindAllAccounts_Empty() throws Exception {
        when(accountService.findAllAccounts()).thenReturn(Collections.emptyList());

        Response actualResponse = accountController.findAllAccounts();
        assertNotNull(actualResponse);
        assertEquals(200, actualResponse.getCode());
        assertEquals("No se ha encontrado ninguna cuenta.", actualResponse.getMessage());

        verify(accountService, times(1)).findAllAccounts();
    }

    @Test
    void testFindAccountsByDocumentId_Success() throws Exception {
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        when(accountService.findAccountsByDocumentId(anyString())).thenReturn(accounts);

        Response actualResponse = accountController.findAllAccounts("12345678");
        assertNotNull(actualResponse);
        assertEquals(200, actualResponse.getCode());
        assertEquals("Cuentas encontradas:", actualResponse.getMessage());

        verify(accountService, times(1)).findAccountsByDocumentId(anyString());
    }

    @Test
    void testFindAccountsByDocumentId_Empty() throws Exception {
        when(accountService.findAccountsByDocumentId(anyString())).thenReturn(Collections.emptyList());

        Response actualResponse = accountController.findAllAccounts("12345678");
        assertNotNull(actualResponse);
        assertEquals(200, actualResponse.getCode());
        assertEquals("No se ha encontrado ninguna cuenta.", actualResponse.getMessage());

        verify(accountService, times(1)).findAccountsByDocumentId(anyString());
    }
}