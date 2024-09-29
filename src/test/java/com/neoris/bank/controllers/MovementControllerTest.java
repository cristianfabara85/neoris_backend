package com.neoris.bank.controllers;

import com.google.gson.Gson;
import com.neoris.bank.models.Movement;
import com.neoris.bank.models.MovementDto;
import com.neoris.bank.models.Response;
import com.neoris.bank.services.MovementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

public class MovementControllerTest {

    @Mock
    private MovementService movementService;

    private Gson gson =new Gson();

    @InjectMocks
    private MovementController movementController;

    private Movement movement;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        movement = new Movement();
        movementController.setGson(gson);
    }

    @Test
    void testCreateMovementSuccess() throws Exception {
        movement.setId(1L);
        when(movementService.createMovement(anyLong(), any(Movement.class))).thenReturn(movement);

        Response response = movementController.createMovement(12345L, movement);

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertNotNull(response.getData());
        assertEquals("Se ha creado el siguiente movimiento:", response.getMessage());
    }

    @Test
    void testCreateMovementFailure() throws Exception {
        when(movementService.createMovement(anyLong(), any(Movement.class))).thenReturn(new Movement());

        Response response = movementController.createMovement(12345L, movement);

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals("No se ha creado ningun movimiento.", response.getMessage());
    }

    @Test
    void testSearchBalanceSuccess() throws Exception {
        movement.setBalance(BigDecimal.valueOf(1000.0));
        when(movementService.searchBalance(anyLong())).thenReturn(movement);

        Response response = movementController.searchBalance(12345L);

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals("El saldo de la cuenta es:", response.getMessage());
    }

    @Test
    void testSearchBalanceFailure() throws Exception {
        when(movementService.searchBalance(anyLong())).thenReturn(new Movement());

        Response response = movementController.searchBalance(12345L);

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals("No se ha encontrado ningun movimiento.", response.getMessage());
    }

    @Test
    void testAccountStateSuccess() throws Exception {
        MovementDto movementDto = new MovementDto();
        List<MovementDto> movementDtoList = Collections.singletonList(movementDto);
        when(movementService.accountState(anyString(), anyString())).thenReturn(movementDtoList);

        Response response = movementController.accountState("documentId", "fromDate");

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals("estado de cuenta generado:", response.getMessage());
    }

    @Test
    void testAccountStateFailure() throws Exception {
        when(movementService.accountState(anyString(), anyString())).thenReturn(Collections.emptyList());

        Response response = movementController.accountState("documentId", "fromDate");

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals("No se ha encontrado ningun movimiento.", response.getMessage());
    }
}