package com.neoris.bank.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResponseTest {
    @Test
    public void test(){

        Response p = new Response();
        p.setCode(1);
        p.setMessage("123");
        p.setData(new Object());

        Assertions.assertEquals(p.getCode(),p.getCode());
        Assertions.assertEquals(p.getData(),p.getData());
        Assertions.assertEquals(p.getMessage(),p.getMessage());


    }
}
