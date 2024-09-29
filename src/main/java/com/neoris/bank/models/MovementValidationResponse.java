package com.neoris.bank.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovementValidationResponse {
    private Boolean valid;
    private String message;
    private Movement movementCalculated;
}
