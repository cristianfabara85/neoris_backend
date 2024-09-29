package com.neoris.bank.services;

import com.neoris.bank.models.Client;

import java.util.List;

public interface ClientService {

	Client createClient(Client client);

	Client deleteClient(String documentId);

	Client updateClient(Long clientId, Client client);

	List<Client> findAllCLients();

}
