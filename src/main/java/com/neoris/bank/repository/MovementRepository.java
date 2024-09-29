package com.neoris.bank.repository;

import com.neoris.bank.models.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface MovementRepository extends JpaRepository<Movement,Long> {

    @Query(value = "select * from movements where account_id=(select id from account where number=:accountNumber) order by transaction_date desc limit 1;", nativeQuery = true)
    Optional<Movement> findLastMovement(Long accountNumber);

    @Query(value = "select m.id,m.transaction_date,p.name,a.number,ct.description as type,a.initial_balance as initialBalance,cs.description as state, cm.description as movement_type,m.amount,m.balance  from movements m  inner join catalog cm on cm.id=m.type inner join account a on a.id =m.account_id inner join catalog ct on ct.id =a.type inner join catalog cs on cs.id =a.state inner join client cl on cl.id =a.client_id inner join person p on p.person_id =cl.person_id  where p.document_id =:documentId and m.transaction_date >= :fromDate  and m.transaction_date < current_timestamp   order by a.number,m.transaction_date  ", nativeQuery = true)
    Optional<List<Object>> findAccountState(String documentId, Instant fromDate);



}
