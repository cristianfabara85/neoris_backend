package com.neoris.bank.controllers;

import com.google.gson.Gson;
import com.neoris.bank.models.Person;
import com.neoris.bank.models.Response;
import com.neoris.bank.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.PUT, RequestMethod.GET, RequestMethod.POST,
		RequestMethod.DELETE })
@RequestMapping("/api/personas")
public class PersonController {
	
Logger logger = LoggerFactory.getLogger(PersonController.class);
	
	@Autowired
	private PersonService personService;

	@Autowired
	private Gson gson;
	
	@PostMapping("/crearPersona")
	public Response createPerson(@Valid @RequestBody Person person){
		logger.info("Creando nueva Persona...");
		Response response= new Response();

			Person personCreated=personService.createPerson(person);
			if(personCreated.getPersonId()==null) {
				response.setCode(200);
				response.setMessage("No se ha creado ninguna persona.");
				response.setData(personCreated);
				logger.info(gson.toJson(response));
			}else{
				mapPersonResponse(response, personCreated,"Se ha creado/actualizado la siguiente persona:");
			}
			return response;
	}
	
	@PutMapping("/actualizarPersona")
	public Response updatePerson(@RequestParam Long  personId,@RequestBody Person person)throws Exception {
		logger.info("Actualizando Persona por id...");
		Response response= new Response();
			Person personCreated=personService.updatePerson(personId, person);
			if(personCreated.getPersonId()==null) {
				response.setCode(200);
				response.setMessage("No se ha actualizado ninguna persona.");
				response.setData(personCreated);
				logger.info(gson.toJson(response));
			}else{
				mapPersonResponse(response, personCreated,"Se ha actualizado la siguiente persona:");
			}
			return response;
	}
	
	@DeleteMapping("/borrarPersona")
	public Response deletePerson(@RequestParam String documentId)throws Exception {
		logger.info("Eliminando Persona por id...");
		Response response= new Response();
			Person personDeleted=personService.deletePerson(documentId);
			if(personDeleted.getPersonId()==null) {
				response.setCode(200);
				response.setMessage("No se ha eliminado ninguna persona.");
				response.setData(personDeleted);
				logger.info(gson.toJson(response));
			}else{
				mapPersonResponse(response, personDeleted,"persona eliminada correctamente");
			}
			return response;
	}
	
	private void mapPersonResponse(Response response, Person person,String message) {
		response.setCode(200);
		response.setMessage(message);
		response.setData(person);
		logger.info(gson.toJson(response));
	}

	public void setGson(Gson gson) {
		this.gson = gson;
	}
}
