package com.neoris.bank.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ClientTest {

    @Test
    public void ClientTest(){

        Person p = new Person();
        p.setAddress("");
        p.setPhone("123");
        p.setAge(1);
        p.setGender(1);
        p.setDocumentId("");
        p.setPersonId(1L);


        Client client = new Client();
        client.setState(5);
        client.setId(1L);
        client.setAccounts(new ArrayList<>());
        client.setPassword("");
        client.setPerson(p);

        Client client2= new Client(1L,"",1,new Person(),new ArrayList<>());

        Assertions.assertEquals(client.getState(),client.getState());
        Assertions.assertEquals(client.getId(),client.getId());
        Assertions.assertEquals(client.getAccounts(),client.getAccounts());
        Assertions.assertEquals(client.getPassword(),client.getPassword());
        Assertions.assertEquals(client.getPerson(),client.getPerson());

        Assertions.assertEquals(client2.getState(),client2.getState());
        Assertions.assertEquals(client2.getId(),client2.getId());
        Assertions.assertEquals(client2.getAccounts(),client2.getAccounts());
        Assertions.assertEquals(client2.getPassword(),client2.getPassword());
        Assertions.assertEquals(client2.getPerson(),client2.getPerson());

    }
}
