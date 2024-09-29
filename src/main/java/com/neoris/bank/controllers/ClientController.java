package com.neoris.bank.controllers;

import com.google.gson.Gson;
import com.neoris.bank.models.Client;
import com.neoris.bank.models.Response;
import com.neoris.bank.services.ClientService;
import com.neoris.bank.util.BankUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.PUT, RequestMethod.GET, RequestMethod.POST,
		RequestMethod.DELETE })
@RequestMapping("/api/clientes")
public class ClientController {
	
Logger logger = LoggerFactory.getLogger(PersonController.class);
	
	@Autowired
	private ClientService clientService;

	@Autowired
	private Gson gson;
	
	@PostMapping("/crearCliente")
	public Response createClient(@Valid @RequestBody Client client)throws Exception {
		logger.info("Creando nueva Persona...");
		Response response= new Response();
			Client clientCreated=clientService.createClient(client);
			if(clientCreated.getPerson()==null||clientCreated.getPerson().getPersonId()==null) {
				response.setCode(200);
				response.setMessage("No se ha creado ningun cliente.");
				response.setData(clientCreated);
				logger.info(gson.toJson(response));
			}else{
				BankUtils.mapResponse(response,  Collections.singletonList(clientCreated),"Se ha creado/actualizado el siguiente cliente:");
			}
			return response;
	}
	
	@PutMapping("/actualizarCliente")
	public Response updateClient(@RequestParam Long  clientId,@RequestBody Client client)throws Exception {
		logger.info("Actualizando cliente por id...");
		Response response= new Response();
			Client clientUpdated=clientService.updateClient(clientId, client);
			if(clientUpdated.getPerson()==null || clientUpdated.getPerson().getPersonId()==null) {
				response.setCode(200);
				response.setMessage("No se ha actualizado ningun cliente.");
				response.setData(clientUpdated);
				logger.info(gson.toJson(response));
			}else{
				BankUtils.mapResponse(response,  Collections.singletonList(clientUpdated),"Se ha actualizado el siguiente cliente:");
			}
			return response;
	}
	
	@DeleteMapping("/borrarCliente")
	public Response deleteClient(@RequestParam String documentId)throws Exception {
		logger.info("Eliminando cliente por id...");
		Response response= new Response();
			Client clientDeleted=clientService.deleteClient(documentId);
			if(clientDeleted.getPerson()==null ||clientDeleted.getPerson().getPersonId()==null) {
				response.setCode(200);
				response.setMessage("No se ha eliminado ningun cliente.");
				response.setData(clientDeleted);
				logger.info(gson.toJson(response));
			}else{
				BankUtils.mapResponse(response, Collections.singletonList(clientDeleted),"cliente eliminado correctamente");
			}
			return response;
	}

	@GetMapping("/")
	public Response findAllClients()throws Exception {
		logger.info("Listado de clientes...");
		Response response= new Response();
			List<Client> clientCreated=clientService.findAllCLients();
			if(clientCreated.isEmpty()) {
				response.setCode(200);
				response.setMessage("No se ha encontrado ningun cliente.");
				response.setData(clientCreated);
				logger.info(gson.toJson(response));
			}else{
				BankUtils.mapResponse(response, Collections.singletonList(clientCreated),"Se ha encontrado los siguientes clientes:");
			}
			return response;
	}

	public void setGson(Gson gson) {
		this.gson = gson;
	}

}
