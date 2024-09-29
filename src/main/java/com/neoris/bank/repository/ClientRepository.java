package com.neoris.bank.repository;

import com.neoris.bank.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends  JpaRepository<Client, Long>  {
	
    @Query(value = "SELECT * FROM public.client c inner join person p on p.person_id=c.person_id where p.document_Id=:documentId", nativeQuery = true)
	Optional<Client> findclientByDocumentId(String documentId);

}
