package com.neoris.bank.controllers;

import com.google.gson.Gson;
import com.neoris.bank.models.Movement;
import com.neoris.bank.models.MovementDto;
import com.neoris.bank.models.Response;
import com.neoris.bank.services.MovementService;
import com.neoris.bank.util.BankUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.PUT, RequestMethod.GET, RequestMethod.POST,
		RequestMethod.DELETE })
@RequestMapping("/api/movimientos")
public class MovementController {

	Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private MovementService movementService;

	@Autowired
	private Gson gson;

	@PostMapping("/")
	public Response createMovement(@NotNull @RequestParam Long accountNumber, @Valid @RequestBody Movement movement)throws Exception {
		logger.info("Creando nuevo movimiento pra la cuenta "+accountNumber+" ...");
		Response response= new Response();
			Movement movementCreated=movementService.createMovement(accountNumber,movement);
			if(movementCreated.getId()==null) {
				response.setCode(200);
				response.setMessage("No se ha creado ningun movimiento.");
				response.setData(movementCreated);
				logger.info(gson.toJson(response));
			}else{
				BankUtils.mapResponse(response, Collections.singletonList(movementCreated),"Se ha creado el siguiente movimiento:");
			}
			return response;
	}

	@GetMapping("/")
	public Response searchBalance(@RequestParam Long accountNumber)throws Exception {
		logger.info("Consultado saldos... ");
		Response response= new Response();
			Movement movementCreated=movementService.searchBalance(accountNumber);
			if(movementCreated.getBalance()==null) {
				response.setCode(200);
				response.setMessage("No se ha encontrado ningun movimiento.");
				response.setData(movementCreated);
				logger.info(gson.toJson(response));
			}else{
				BankUtils.mapResponse(response, Collections.singletonList(movementCreated.getBalance()),"El saldo de la cuenta es:");
			}
			return response;
	}

	@GetMapping("/accountState")
	public Response accountState(@RequestParam String documentId, String fromDate)throws Exception {
		logger.info("Consultado saldos... ");
		Response response= new Response();
			List<MovementDto> movementCreated=movementService.accountState(documentId,fromDate);
			if(movementCreated.isEmpty()) {
				response.setCode(200);
				response.setMessage("No se ha encontrado ningun movimiento.");
				response.setData(movementCreated);
				logger.info(gson.toJson(response));
			}else{
				BankUtils.mapResponse(response, Collections.singletonList(movementCreated),"estado de cuenta generado:");
			}
			return response;
	}

	public void setGson(Gson gson) {
		this.gson = gson;
	}

}
