package com.neoris.bank.controllers;

import com.google.gson.Gson;
import com.neoris.bank.models.Person;
import com.neoris.bank.models.Response;
import com.neoris.bank.services.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

public class PersonControllerTest {

    @Mock
    private PersonService personService;

    private Gson gson=new Gson();

    @InjectMocks
    private PersonController personController;

    private Person person;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        person = new Person();
        person.setPersonId(1L);
        person.setName("John Doe");

        personController.setGson(gson);
    }

    @Test
    void testCreatePersonSuccess() {
        when(personService.createPerson(any(Person.class))).thenReturn(person);

        Response response = personController.createPerson(person);

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals("Se ha creado/actualizado la siguiente persona:", response.getMessage());
        assertEquals(person, response.getData());
    }

    @Test
    void testCreatePersonFailure() {
        when(personService.createPerson(any(Person.class))).thenReturn(new Person());

        Response response = personController.createPerson(new Person());

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals("No se ha creado ninguna persona.", response.getMessage());
    }

    @Test
    void testUpdatePersonSuccess() throws Exception {
        when(personService.updatePerson(anyLong(), any(Person.class))).thenReturn(person);

        Response response = personController.updatePerson(1L, person);

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals("Se ha actualizado la siguiente persona:", response.getMessage());
        assertEquals(person, response.getData());
    }

    @Test
    void testUpdatePersonFailure() throws Exception {
        when(personService.updatePerson(anyLong(), any(Person.class))).thenReturn(new Person());

        Response response = personController.updatePerson(1L, new Person());

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals("No se ha actualizado ninguna persona.", response.getMessage());
    }

    @Test
    void testDeletePersonSuccess() throws Exception {
        when(personService.deletePerson(anyString())).thenReturn(person);

        Response response = personController.deletePerson("document123");

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals("persona eliminada correctamente", response.getMessage());
        assertEquals(person, response.getData());
    }

    @Test
    void testDeletePersonFailure() throws Exception {
        when(personService.deletePerson(anyString())).thenReturn(new Person());

        Response response = personController.deletePerson("document123");

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals("No se ha eliminado ninguna persona.", response.getMessage());
    }
}