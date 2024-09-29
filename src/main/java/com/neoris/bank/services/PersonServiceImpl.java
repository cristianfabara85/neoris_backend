package com.neoris.bank.services;

import com.neoris.bank.models.Person;
import com.neoris.bank.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {
	
	@Autowired
	private PersonRepository personRepository;

	@Override
	public Person createPerson(Person person) {
		
		Optional<Person> persona=personRepository.findPersonByDocumentId(person.getDocumentId());
		if (persona.isPresent()) {
			Person personUpdate = persona.get();
			mapPersonToUpdate(person, personUpdate);
			return new ResponseEntity<>(personRepository.save(personUpdate), HttpStatus.OK).getBody();
		} else {
			return new ResponseEntity<>(personRepository.save(person),HttpStatus.OK).getBody();
		}
	}



	private void mapPersonToUpdate(Person person, Person personUpdate) {
		personUpdate.setAddress(person.getAddress());
		personUpdate.setAge(person.getAge());
		personUpdate.setPhone(person.getPhone());
		personUpdate.setDocumentId(person.getDocumentId());
		personUpdate.setName(person.getName());
		personUpdate.setGender(person.getGender());
	}



	@Override
	public Person deletePerson(String documentId) {
		Person resp= new Person();
		Optional<Person> persona=personRepository.findPersonByDocumentId(documentId);
		if (persona.isPresent()) {
			personRepository.deleteById(persona.get().getPersonId());
			return new ResponseEntity<>(persona.get(),HttpStatus.OK).getBody();
		} else {
			return new ResponseEntity<>(new Person(),HttpStatus.OK).getBody();
		}
		
	}



	@Override
	public Person updatePerson(Long personId, Person person) {
			Optional<Person> persona=personRepository.findById(personId);
			if (persona.isPresent()) {
				Person personUpdate = persona.get();
				mapPersonToUpdate(person, personUpdate);
				return new ResponseEntity<>(personRepository.save(personUpdate), HttpStatus.OK).getBody();
			} else {
				return new ResponseEntity<>(new Person(),HttpStatus.OK).getBody();
			}
	}

}
