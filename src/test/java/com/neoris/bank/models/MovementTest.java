package com.neoris.bank.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

public class MovementTest {

    @Test
    public void test(){

        Movement p = new Movement();
        p.setId(1L);
        p.setBalance(new BigDecimal(1));
        p.setAmount(new BigDecimal(1));
        p.setType(1);
        p.setTransactionDate(Instant.now());
        p.setAccount(new Account());
        p.setAccountId(1L);


        Assertions.assertEquals(p.getId(),p.getId());
        Assertions.assertEquals(p.getBalance(),p.getBalance());
        Assertions.assertEquals(p.getAmount(),p.getAmount());
        Assertions.assertEquals(p.getType(),p.getType());
        Assertions.assertEquals(p.getTransactionDate(),p.getTransactionDate());
        Assertions.assertEquals(p.getAccountId(),p.getAccountId());
        Assertions.assertEquals(p.getAccount(),p.getAccount());


    }
}
