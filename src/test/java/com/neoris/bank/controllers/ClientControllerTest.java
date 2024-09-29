package com.neoris.bank.controllers;

import com.google.gson.Gson;
import com.neoris.bank.models.Client;
import com.neoris.bank.models.Person;
import com.neoris.bank.models.Response;
import com.neoris.bank.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ClientControllerTest {

    @Mock
    private ClientService clientService;

    private Gson gson = new Gson();

    @InjectMocks
    private ClientController clientController;

    private Client client;
    private Response response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        client = new Client();
        client.setPerson(new Person());
        client.getPerson().setPersonId(1L);
        response = new Response();
        clientController.setGson(gson);
    }

    @Test
    void testCreateClientSuccess() throws Exception {
        when(clientService.createClient(any(Client.class))).thenReturn(client);
        Response response = clientController.createClient(client);

        assertNotNull(response);
        assertEquals(200, response.getCode());
        verify(clientService, times(1)).createClient(any(Client.class));
    }

    @Test
    void testCreateClientFailure() throws Exception {
        when(clientService.createClient(any(Client.class))).thenReturn(new Client());
        Response response = clientController.createClient(client);

        assertNotNull(response);
        assertEquals(200, response.getCode());
        verify(clientService, times(1)).createClient(any(Client.class));
    }

    @Test
    void testUpdateClientSuccess() throws Exception {
        when(clientService.updateClient(anyLong(), any(Client.class))).thenReturn(client);
        Response response = clientController.updateClient(1L, client);

        assertNotNull(response);
        assertEquals(200, response.getCode());
        verify(clientService, times(1)).updateClient(anyLong(), any(Client.class));
    }

    @Test
    void testUpdateClientFailure() throws Exception {
        when(clientService.updateClient(anyLong(), any(Client.class))).thenReturn(new Client());

        Response response = clientController.updateClient(1L, client);
        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals("No se ha actualizado ningun cliente.", response.getMessage());
    }

    @Test
    void testDeleteClientSuccess() throws Exception {
        when(clientService.deleteClient(anyString())).thenReturn(client);
        Response response = clientController.deleteClient("123");

        assertNotNull(response);
        assertEquals(200, response.getCode());
        verify(clientService, times(1)).deleteClient(anyString());
    }

    @Test
    void testDeleteClientFailure() throws Exception {
        when(clientService.deleteClient(anyString())).thenReturn(new Client());

        Response response = clientController.deleteClient("1");
        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals("No se ha eliminado ningun cliente.", response.getMessage());
    }

    @Test
    void testFindAllClientsSuccess() throws Exception {
        when(clientService.findAllCLients()).thenReturn(Collections.singletonList(client));
        Response response = clientController.findAllClients();

        assertNotNull(response);
        assertEquals(200, response.getCode());
        verify(clientService, times(1)).findAllCLients();
    }

    @Test
    void testFindAllClientsFailure() throws Exception {
        when(clientService.findAllCLients()).thenReturn(Collections.emptyList());
        Response response = clientController.findAllClients();

        assertNotNull(response);
        assertEquals(200, response.getCode());
        verify(clientService, times(1)).findAllCLients();
    }
}