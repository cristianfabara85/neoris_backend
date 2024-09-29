package com.neoris.bank.controllers;

import com.google.gson.Gson;
import com.neoris.bank.models.Account;
import com.neoris.bank.models.Response;
import com.neoris.bank.services.AccountService;
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
@RequestMapping("/api/cuentas")
public class AccountController {

	Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AccountService accountService;

	@Autowired
	private Gson gson;

	@PostMapping("/")
	public Response createAccount( @Valid @RequestBody Account account)throws Exception {
		logger.info("Creando nueva cuenta...");
		Response response= new Response();
			Account accountCreated=accountService.createAccount(account);
			if(accountCreated.getId()==null) {
				response.setCode(200);
				response.setMessage("No se ha creado ninguna cuenta.");
				response.setData(accountCreated);
				logger.info(gson.toJson(response));
			}else{
				BankUtils.mapResponse(response,  Collections.singletonList(accountCreated),"Se ha creado/actualizado la siguiente cuenta:");
			}
			return response;
	}

	@PutMapping("/")
	public Response updateAccount(@Valid @RequestParam Long  accountNumber,@RequestBody Account account)throws Exception {
		logger.info("Actualizando cuenta por id...");
		Response response= new Response();
			Account accountUpdated=accountService.updateAccount(accountNumber, account);
			if(accountUpdated.getId()==null) {
				response.setCode(200);
				response.setMessage("No se ha actualizado ninguna cuenta.");
				response.setData(accountUpdated);
				logger.info(gson.toJson(response));
			}else{
				BankUtils.mapResponse(response,  Collections.singletonList(accountUpdated),"Se ha actualizado la siguiente cuenta:");
			}
			return response;
	}

	@DeleteMapping("/")
	public Response deleteAccount(@RequestParam Long accountNumber)throws Exception {
		logger.info("Eliminando cuenta por id...");
		Response response= new Response();
			Account accountDeleted=accountService.deleteAccount(accountNumber);
			if(accountDeleted.getId()==null) {
				response.setCode(200);
				response.setMessage("No se ha eliminado ninguna cuenta.");
				response.setData(accountDeleted);
				logger.info(gson.toJson(response));
			}else{
				BankUtils.mapResponse(response, Collections.singletonList(accountDeleted),"cuenta eliminada correctamente");
			}
			return response;
	}

	@GetMapping("/all")
	public Response findAllAccounts()throws Exception {
		logger.info("Listado de cuentas...");
		Response response= new Response();
			List<Account> accountCreated=accountService.findAllAccounts();
			if(accountCreated.isEmpty()) {
				response.setCode(200);
				response.setMessage("No se ha encontrado ninguna cuenta.");
				response.setData(accountCreated);
				logger.info(gson.toJson(response));
			}else{
				BankUtils.mapResponse(response, Collections.singletonList(accountCreated),"Cuentas encontradas:");
			}
			return response;
	}

	@GetMapping("/")
	public Response findAllAccounts(@NotNull @RequestParam String documentId)throws Exception {
		logger.info("Listado de cuentas por ID "+documentId+"...");
		Response response= new Response();
			List<Account> accountCreated=accountService.findAccountsByDocumentId(documentId);
			if(accountCreated.isEmpty()) {
				response.setCode(200);
				response.setMessage("No se ha encontrado ninguna cuenta.");
				response.setData(accountCreated);
				logger.info(gson.toJson(response));
			}else{
				BankUtils.mapResponse(response, Collections.singletonList(accountCreated),"Cuentas encontradas:");
			}
			return response;
	}

	public void setGson(Gson gson) {
		this.gson = gson;
	}

}
