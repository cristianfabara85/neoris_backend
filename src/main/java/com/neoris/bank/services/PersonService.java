package com.neoris.bank.services;

import com.neoris.bank.models.Person;

public interface PersonService {
	
	Person createPerson(Person person);
	
	Person updatePerson(Long personId,Person person);
	
	Person deletePerson(String documentId);

}
