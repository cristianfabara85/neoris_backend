package com.neoris.bank.services;

import com.neoris.bank.models.Movement;
import com.neoris.bank.models.MovementDto;

import java.util.List;

public interface MovementService {

    Movement createMovement(Long accountNumber, Movement movement);

    Movement searchBalance(Long accountNumber);

    //List<MovementReport> accountState(String documentId, String fromDate);

    List<MovementDto> accountState(String documentId, String fromDate);
}
