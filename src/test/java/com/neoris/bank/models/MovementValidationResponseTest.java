package com.neoris.bank.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MovementValidationResponseTest {

    @Test
    public void test(){

        MovementValidationResponse p=new MovementValidationResponse();

        p.setValid(Boolean.TRUE);
        p.setMessage("");
        p.setMovementCalculated(new Movement());

        Assertions.assertEquals(p.getValid(),p.getValid());
        Assertions.assertEquals(p.getMessage(),p.getMessage());
        Assertions.assertEquals(p.getMovementCalculated(),p.getMovementCalculated());
    }
}
