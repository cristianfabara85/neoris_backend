package com.neoris.bank.services;

import com.neoris.bank.models.*;
import com.neoris.bank.repository.AccountRepository;
import com.neoris.bank.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovementServiceImpl implements MovementService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MovementRepository movementRepository;

    @Override
    public Movement createMovement(Long accountNumber, Movement movement) {
        MovementValidationResponse validation = validateBalance(accountNumber, movement);
        if (validation.getValid()) {
            movement.setTransactionDate(Instant.now());
            movementRepository.save(movement);
            return movement;
        } else {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, validation.getMessage());
        }
    }

    private MovementValidationResponse validateBalance(Long accountNumber, Movement movement) {
        MovementValidationResponse response = new MovementValidationResponse();
        response.setValid(Boolean.TRUE);
        Optional<Account> account = accountRepository.findAccountByNumber(accountNumber);
        if (account.isPresent()) {
            Optional<Movement> lastMovement = movementRepository.findLastMovement(accountNumber);
            if (!lastMovement.isPresent()) {
                response.setValid(Boolean.FALSE);
                response.setMessage("No Es posible encontrar el ultimo movimiento");
                return response;
            } else {
                Movement mov = lastMovement.get();
                movement.setAccountId(account.get().getId());
                if ((mov.getBalance()).setScale(2).equals(BigDecimal.ZERO.setScale(2)) && movement.getType() == 12) {
                    response.setValid(Boolean.FALSE);
                    response.setMovementCalculated(movement);
                    response.setMessage("No se puede realizar un retiro de una cuenta con saldo 0");
                    return response;
                } else {
                    generateMovement(mov, movement,response);
                    if(response.getValid()) {
                        response.setMovementCalculated(movement);
                        response.setMessage("Transaccion aprobada");
                    }
                }
                return response;
            }
        } else {
            response.setValid(Boolean.FALSE);
            response.setMessage("No Existe la cuenta solicitada");
            return response;
        }
    }

    private void generateMovement(Movement lastMovement, Movement movement,MovementValidationResponse response) {
        if (movement.getType().equals(11)) {
            movement.setBalance(lastMovement.getBalance().add(movement.getAmount()));
        } else if (movement.getType().equals(12)) {
            if(movement.getAmount().compareTo(lastMovement.getBalance())>0){
                   response.setValid(Boolean.FALSE);
                   response.setMessage("El monto de retiro no puede ser mayor al saldo disponible");
            }else{
                movement.setBalance(lastMovement.getBalance().subtract(movement.getAmount()));
            }
        }
    }


    @Override
    public Movement searchBalance(Long accountNumber) {
        MovementValidationResponse validation = getBalance(accountNumber);
        if (validation.getValid()) {
            return validation.getMovementCalculated();
        } else {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, validation.getMessage());
        }
    }
    @Override
    public List<MovementDto> accountState(String documentId, String fromDate) {
        Instant instant = getInstant(fromDate);
        Optional<List<Object>> movements = movementRepository.findAccountState(documentId, instant);
        if (movements.isPresent()) {
            return mapReportItem(movements.get());
        } else {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "No se encontraron registros");
        }
    }

    private static List<MovementDto> mapReportItem(List<Object> movements) {
        return movements.stream().map(object -> {
            Object[] obj = (Object[]) object;
            MovementDto mr = new MovementDto();
            mr.setId(Long.parseLong(obj[0].toString()));
            mr.setTransactionDate(obj[1].toString());
            mr.setName(obj[2].toString());
            mr.setNumber(Long.valueOf(obj[3].toString()));
            mr.setType(obj[4].toString());
            mr.setInitialBalance(Double.valueOf(obj[5].toString()));
            mr.setState(obj[6].toString());
            mr.setMovementType(obj[7].toString());
            mr.setAmount(Double.valueOf(obj[8].toString()));
            mr.setBalance(Double.valueOf(obj[9].toString()));
            return mr;
        }).collect(Collectors.toList());
    }

    private static Instant getInstant(String fromDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(fromDate, formatter);
        return localDateTime.toInstant(ZoneOffset.UTC);
    }

    public MovementValidationResponse getBalance(Long accountNumber){
        MovementValidationResponse response = new MovementValidationResponse();
        response.setValid(Boolean.TRUE);
        Optional<Account> account = accountRepository.findAccountByNumber(accountNumber);
        if (account.isPresent()) {
            Optional<Movement> lastMovement = movementRepository.findLastMovement(accountNumber);
            if (lastMovement.isPresent()) {
                response.setMovementCalculated(lastMovement.get());
            }else{
                response.setValid(Boolean.FALSE);
                response.setMessage("No se han encontrado movimientos");
            }
        } else {
            response.setValid(Boolean.FALSE);
            response.setMessage("No Existe la cuenta solicitada");

        }
        return response;
    }
}
