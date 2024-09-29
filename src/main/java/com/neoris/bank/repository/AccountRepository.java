package com.neoris.bank.repository;

import com.neoris.bank.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {

    @Query(value = "SELECT * FROM account a inner join client c on a.client_id=c.id where a.number=:number and a.state=13", nativeQuery = true)
    Optional<Account> findAccountByNumber(Long number);

    @Query(value = "SELECT * FROM account a inner join client c on a.client_id=c.id where a.client_id=:clientId and a.state=13", nativeQuery = true)
    Optional<List<Account>> findAccountsByClientId(Long clientId);

    @Query(value = "SELECT * FROM account a inner join client c on a.client_id=c.id inner join person p on p.person_id=c.person_id where p.document_id=:documentId and a.state=13", nativeQuery = true)
    Optional<List<Account>> findAccountsByDocumentId(String documentId);


}
