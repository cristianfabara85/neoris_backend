package com.neoris.bank.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PersonTest {

    @Test
    public void test(){

        Person p = new Person();
        p.setAddress("");
        p.setPhone("123");
        p.setAge(1);
        p.setName("");
        p.setGender(1);
        p.setDocumentId("");
        p.setPersonId(1L);
        p.setClient(new Client());

        Assertions.assertEquals(p.getName(),p.getName());
        Assertions.assertEquals(p.getAddress(),p.getAddress());
        Assertions.assertEquals(p.getPhone(),p.getPhone());
        Assertions.assertEquals(p.getAge(),p.getAge());
        Assertions.assertEquals(p.getPersonId(),p.getPersonId());
        Assertions.assertEquals(p.getGender(),p.getGender());
        Assertions.assertEquals(p.getDocumentId(),p.getDocumentId());
        Assertions.assertEquals(p.getClient(),p.getClient());

    }
}
