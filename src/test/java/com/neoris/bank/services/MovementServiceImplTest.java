package com.neoris.bank.services;

import com.neoris.bank.models.*;
import com.neoris.bank.repository.AccountRepository;
import com.neoris.bank.repository.MovementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class MovementServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private MovementRepository movementRepository;

    @InjectMocks
    private MovementServiceImpl movementService;

    private Account account;
    private Movement movement;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        account = new Account();
        account.setId(1L);
        account.setNumber(123L);
        account.setInitialBalance(BigDecimal.valueOf(1000));

        movement = new Movement();
        movement.setType(12);
        movement.setAmount(BigDecimal.valueOf(100));
    }

    @Test
    void testCreateMovementWhenBalanceIsValid() {
        Movement lastMovement = new Movement();
        lastMovement.setBalance(BigDecimal.valueOf(500));
        when(accountRepository.findAccountByNumber(anyLong())).thenReturn(Optional.of(account));
        when(movementRepository.findLastMovement(anyLong())).thenReturn(Optional.of(lastMovement));
        when(movementRepository.save(any(Movement.class))).thenReturn(movement);

        Movement result = movementService.createMovement(account.getNumber(), movement);

        assertNotNull(result);
        verify(movementRepository, times(1)).save(any(Movement.class));
    }

    @Test
    void testCreateMovementWhenBalanceNoValid() {
        Movement lastMovement = new Movement();
        lastMovement.setBalance(BigDecimal.valueOf(500));
        when(accountRepository.findAccountByNumber(anyLong())).thenReturn(Optional.empty());
        when(movementRepository.findLastMovement(anyLong())).thenReturn(Optional.of(lastMovement));
        when(movementRepository.save(any(Movement.class))).thenReturn(movement);

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> movementService.createMovement(account.getNumber(), movement));

        assertTrue(exception.getMessage().contains("No Existe la cuenta solicitada"));
    }

    @Test
    void testCreateMovementWhenBalanceIsInvalid() {
        Movement lastMovement = new Movement();
        lastMovement.setBalance(BigDecimal.valueOf(50));
        when(accountRepository.findAccountByNumber(anyLong())).thenReturn(Optional.of(account));
        when(movementRepository.findLastMovement(anyLong())).thenReturn(Optional.of(lastMovement));

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> movementService.createMovement(account.getNumber(), movement));

        assertTrue(exception.getMessage().contains("El monto de retiro no puede ser mayor al saldo disponible"));
        verify(movementRepository, never()).save(any(Movement.class));
    }

    @Test
    void testCreateMovementWhenBalanceIsZero() {
        Movement lastMovement = new Movement();
        lastMovement.setBalance(BigDecimal.valueOf(0));
        when(accountRepository.findAccountByNumber(anyLong())).thenReturn(Optional.of(account));
        when(movementRepository.findLastMovement(anyLong())).thenReturn(Optional.of(lastMovement));

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> movementService.createMovement(account.getNumber(), movement));

        assertTrue(exception.getMessage().contains("No se puede realizar un retiro de una cuenta con saldo 0"));
        verify(movementRepository, never()).save(any(Movement.class));
    }

    @Test
    void testCreateMovementWhenNoLastMovement() {
        when(accountRepository.findAccountByNumber(anyLong())).thenReturn(Optional.of(account));
        when(movementRepository.findLastMovement(anyLong())).thenReturn(Optional.empty());

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> movementService.createMovement(account.getNumber(), movement));

        assertTrue(exception.getMessage().contains("No Es posible encontrar el ultimo movimiento"));
        verify(movementRepository, never()).save(any(Movement.class));
    }

    @Test
    void testSearchBalanceWhenAccountAndMovementExist() {
        Movement lastMovement = new Movement();
        lastMovement.setBalance(BigDecimal.valueOf(500));
        when(accountRepository.findAccountByNumber(anyLong())).thenReturn(Optional.of(account));
        when(movementRepository.findLastMovement(anyLong())).thenReturn(Optional.of(lastMovement));

        Movement result = movementService.searchBalance(account.getNumber());

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(500), result.getBalance());
    }

    @Test
    void testSearchBalanceWhenNoLastMovement() {
        when(accountRepository.findAccountByNumber(anyLong())).thenReturn(Optional.of(account));
        when(movementRepository.findLastMovement(anyLong())).thenReturn(Optional.empty());

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> movementService.searchBalance(account.getNumber()));

        assertTrue(exception.getMessage().contains("No se han encontrado movimientos"));
    }

    @Test
    void testSearchBalanceWhenAccountDoesNotExist() {
        when(accountRepository.findAccountByNumber(anyLong())).thenReturn(Optional.empty());

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> movementService.searchBalance(account.getNumber()));

        assertTrue(exception.getMessage().contains("No Existe la cuenta solicitada"));
    }

    @Test
    void testAccountStateWhenNoMovementsExist() {
        when(movementRepository.findAccountState(anyString(), any(Instant.class)))
                .thenReturn(Optional.empty());

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class,
                () -> movementService.accountState("123456", "2023/09/27 00:00:00"));

        assertTrue(exception.getMessage().contains("No se encontraron registros"));
    }
}