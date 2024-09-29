package com.neoris.bank.services;

import com.neoris.bank.models.Client;
import com.neoris.bank.models.Person;
import com.neoris.bank.repository.ClientRepository;
import com.neoris.bank.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
        clientRepository=mock(ClientRepository.class);
        personRepository=mock(PersonRepository.class);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateClient_NewClient() {
        clientRepository=mock(ClientRepository.class);
        personRepository=mock(PersonRepository.class);

        // Arrange
        Client client = createSampleClient();
        when(clientRepository.findclientByDocumentId(anyString())).thenReturn(Optional.empty());
        when(personRepository.save(any(Person.class))).thenReturn(client.getPerson());
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        // Act
        Client createdClient = clientService.createClient(client);

        // Assert
        assertNotNull(createdClient);
    }

	@Test
    void testCreateClient_ExistingClient() {
        // Arrange
        Client existingClient = createSampleClient();
        when(clientRepository.findclientByDocumentId(anyString())).thenReturn(Optional.of(existingClient));

        // Act
        Client createdClient = clientService.createClient(existingClient);

        // Assert
        assertNotNull(createdClient);
        assertEquals(existingClient, createdClient);
        verify(clientRepository, times(1)).findclientByDocumentId(existingClient.getPerson().getDocumentId());
        verify(clientRepository, times(1)).save(existingClient);
    }

    @Test
    void testUpdateClient() {

        // Arrange
        Client client = existingClient();
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));
        when(personRepository.save(any(Person.class))).thenReturn(client.getPerson());
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        // Act
        Client createdClient = clientService.updateClient(1L,client);

        // Assert
        assertNotNull(createdClient);
    }

    @Test
    void testUpdateClientEmpty() {

        // Arrange
        Client client = existingClient();
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(personRepository.save(any(Person.class))).thenReturn(client.getPerson());
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        // Act
        Client createdClient = clientService.updateClient(1L,client);

        // Assert
        assertNotNull(createdClient);
    }

    @Test
    void testDeleteClient() {
        // Arrange
        Client existingClient = createSampleClient();
        when(clientRepository.findclientByDocumentId(anyString())).thenReturn(Optional.of(existingClient));

        // Act
        Client createdClient = clientService.deleteClient("1234567890");

        // Assert
        assertNotNull(createdClient);
        assertEquals(existingClient, createdClient);
        verify(clientRepository, times(1)).findclientByDocumentId(existingClient.getPerson().getDocumentId());
    }

    @Test
    void testDeleteClientEmpty() {
        // Arrange
        when(clientRepository.findclientByDocumentId(anyString())).thenReturn(Optional.empty());

        // Act
        Client createdClient = clientService.deleteClient("1234567890");

        // Assert
        assertNotNull(createdClient);
        assertNull(createdClient.getId());
        verify(clientRepository, times(1)).findclientByDocumentId(anyString());
    }

    private Client createSampleClient() {
        Person person = new Person();
        person.setDocumentId("1234567890");
        person.setName("John Doe");
        person.setAge(30);
        person.setAddress("123 Main St");
        person.setPhone("555-1234");

        Client client = new Client();
        client.setPerson(person);
        client.setPassword("password");
        client.setState(13);

        return client;
    }
    private Client existingClient() {
        Person person = new Person();
        person.setDocumentId("1234567890");
        person.setName("John Doe");
        person.setAge(30);
        person.setAddress("123 Main St");
        person.setPhone("555-1234");

        Client client = new Client();
        client.setPerson(person);
        client.setPassword("password");
        client.setState(13);
        client.setId(1L);

        return client;
    }
}