package com.neoris.bank.services;

import com.neoris.bank.models.Client;
import com.neoris.bank.models.Person;
import com.neoris.bank.repository.AccountRepository;
import com.neoris.bank.repository.ClientRepository;
import com.neoris.bank.repository.PersonRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
	
	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Client createClient(Client client) {
		
		Optional<Client> cliente=clientRepository.findclientByDocumentId(client.getPerson().getDocumentId());
		if (cliente.isPresent()) {
			Client clientUpdate = cliente.get();
			mapClientToUpdate(client, clientUpdate);
			clientRepository.save(clientUpdate);
			return clientUpdate;
		} else {
				personRepository.save(client.getPerson());
				clientRepository.save(client);

			return client;
		}
	}



	private void mapClientToUpdate(Client client, Client personUpdate) {
		personUpdate.setState(client.getState());
		personUpdate.setPassword(client.getPassword());
		personUpdate.setPerson((Person)Hibernate.unproxy(personUpdate.getPerson()));
		personUpdate.getPerson().setAge(client.getPerson().getAge());
		personUpdate.getPerson().setAddress(client.getPerson().getAddress());
		personUpdate.getPerson().setPhone(client.getPerson().getPhone());
		personUpdate.getPerson().setName(client.getPerson().getName());
		
	}



	@Override
	public Client deleteClient(String documentId) {
		Optional<Client> cliente=clientRepository.findclientByDocumentId(documentId);
		if (cliente.isPresent()) {
			cliente.get().setPerson((Person)Hibernate.unproxy(cliente.get().getPerson()));
			clientRepository.deleteById(cliente.get().getId());
			return cliente.get();
		} else {
			return new Client();
		}
		
	}


	@Override
	public Client updateClient(Long clientId, Client client) {
			Optional<Client> cliente=clientRepository.findById(clientId);
			if (cliente.isPresent()) {
				Client clientUpdate = cliente.get();
				mapClientToUpdate(client, clientUpdate);
				clientRepository.save(clientUpdate);
				return clientUpdate;
			} else {
				return new Client();
			}
	}

	@Override
	public List<Client> findAllCLients() {
		return clientRepository.findAll().stream().peek(client-> {
			client.setAccounts(accountRepository.findAccountsByClientId(client.getId()).orElse(null));
			client.setPerson((Person)Hibernate.unproxy(client.getPerson()));
		}).collect(Collectors.toList());
	}

}
