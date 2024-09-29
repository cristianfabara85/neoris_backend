package com.neoris.bank.repository;

import com.neoris.bank.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends  JpaRepository<Person, Long>  {
	
    @Query(value = "SELECT * FROM public.person where document_Id=:documentId", nativeQuery = true)
	Optional<Person> findPersonByDocumentId(String documentId);

}
