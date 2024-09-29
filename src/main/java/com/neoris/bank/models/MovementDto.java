package com.neoris.bank.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovementDto {

    private Long id;
    private String transactionDate;
    private String name;
    private Long number;
    private String type;
    private Double initialBalance;
    private String state;
    private String movementType;
    private Double amount;
    private Double balance;
}
