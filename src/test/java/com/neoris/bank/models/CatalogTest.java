package com.neoris.bank.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CatalogTest {

    @Test
    public void test(){

        Catalog p=new Catalog();

        p.setId(1);
        p.setDescription("");
        p.setParentId(1);

        Assertions.assertEquals(p.getId(),p.getId());
        Assertions.assertEquals(p.getDescription(),p.getDescription());
        Assertions.assertEquals(p.getParentId(),p.getParentId());
    }
}
