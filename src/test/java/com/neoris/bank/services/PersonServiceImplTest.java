package com.neoris.bank.services;

import com.neoris.bank.models.Person;
import com.neoris.bank.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    private Person person;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        person = new Person();
        person.setPersonId(1L);
        person.setDocumentId("123456789");
        person.setName("John Doe");
    }

    @Test
    void testCreatePersonWhenPersonExists() {
        when(personRepository.findPersonByDocumentId(anyString())).thenReturn(Optional.of(person));
        when(personRepository.save(any(Person.class))).thenReturn(person);

        Person result = personService.createPerson(person);

        assertNotNull(result);
        assertEquals(person.getPersonId(), result.getPersonId());
    }

    @Test
    void testCreatePersonWhenPersonDoesNotExist() {
        when(personRepository.findPersonByDocumentId(anyString())).thenReturn(Optional.empty());
        when(personRepository.save(any(Person.class))).thenReturn(person);

        Person result = personService.createPerson(person);

        assertNotNull(result);
        assertEquals(person.getPersonId(), result.getPersonId());
      }

    @Test
    void testDeletePersonWhenPersonExists() {
        when(personRepository.findPersonByDocumentId(anyString())).thenReturn(Optional.of(person));

        Person result = personService.deletePerson("123456789");

        assertNotNull(result);
        assertEquals(person.getPersonId(), result.getPersonId());
       }

    @Test
    void testDeletePersonWhenPersonDoesNotExist() {
        when(personRepository.findPersonByDocumentId(anyString())).thenReturn(Optional.empty());

        Person result = personService.deletePerson("123456789");

        assertNotNull(result);
        assertNull(result.getPersonId());
    }

    @Test
    void testUpdatePersonWhenPersonExists() {
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(person));
        when(personRepository.save(any(Person.class))).thenReturn(person);

        Person result = personService.updatePerson(1L, person);

        assertNotNull(result);
        assertEquals(person.getPersonId(), result.getPersonId());
    }

    @Test
    void testUpdatePersonWhenPersonDoesNotExist() {
        when(personRepository.findById(anyLong())).thenReturn(Optional.empty());

        Person result = personService.updatePerson(1L, person);

        assertNotNull(result);
        assertNull(result.getPersonId());
    }
}
