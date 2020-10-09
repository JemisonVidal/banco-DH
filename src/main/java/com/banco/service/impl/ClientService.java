package com.banco.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.banco.dto.ClientDTO;
import com.banco.model.Account;
import com.banco.model.Client;
import com.banco.model.enums.TypeAccount;
import com.banco.repository.AccountRepository;
import com.banco.repository.ClientRepository;
import com.banco.service.IService;
import com.banco.service.exceptions.DataIntegrityException;
import com.banco.service.exceptions.ObjectNotFoundException;

@Service
public class ClientService implements IService<Client> {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Transactional
	@Override
	public Client create(Client object) {
		object.setId(null);
		object = clientRepository.save(object);
		accountRepository.save(object.getAccount());

		return object;
	}

	@Override
	public Client findById(Long id) {
		Optional<Client> obj = clientRepository.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Object not found! Id: " + id + ", type: " + Client.class.getName()));
	}

	@Override
	public List<Client> findAll() {
		return this.clientRepository.findAll();
	}

	@Override
	public Client update(Client object) {
		Client newObject = this.findById(object.getId());
		this.updateData(newObject, object);
		return this.clientRepository.save(newObject);
	}

	@Override
	public void delete(Long id) {
		this.findById(id);
		try {
			this.clientRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("It was not possible to delete the profile");
		}
	}

	public Double saldoCliente(Long id) {
		Client client = this.findById(id);

		return client.getAccount().getSaldo();
	}

	public Double sacar(Long id, Double valor) {
		Client cliente = this.findById(id);
		cliente.getAccount().setSaldo(cliente.getAccount().getSaldo() - valor);
		this.clientRepository.save(cliente);
		return cliente.getAccount().getSaldo();
	}

	public Double depositar(Long id, Double valor) {
		Client cliente = this.findById(id);
		cliente.getAccount().setSaldo(cliente.getAccount().getSaldo() + valor);
		this.clientRepository.save(cliente);
		return cliente.getAccount().getSaldo();
	}

	public void transferir(Long idTransferidor, Long idRecebedor, Double valor) {
		Client clienteTransferidor = this.findById(idTransferidor);
		Client clienteRecebedor = this.findById(idRecebedor);
		clienteTransferidor.getAccount().setSaldo(clienteTransferidor.getAccount().getSaldo() - valor);
		clienteRecebedor.getAccount().setSaldo(clienteRecebedor.getAccount().getSaldo() + valor);
		this.clientRepository.save(clienteTransferidor);
		this.clientRepository.save(clienteRecebedor);
	}

	public Client fromDTO(ClientDTO clientDTO) {
		Client client = new Client(clientDTO.getId(), clientDTO.getName(), clientDTO.getCpf(), clientDTO.getAddress(),
				clientDTO.getAccount());

		Account account = new Account(null, clientDTO.getAccount().getSaldo(), clientDTO.getAccount().getSpecialLimit(),
				TypeAccount.toEnum(clientDTO.getAccount().getTypeAccount()));		
		
		account.setClient(client);
		client.setAccount(account);
		return client;
	}

	private void updateData(Client newObject, Client object) {
		newObject.setId(object.getId());
		newObject.setName(object.getName());
		newObject.setCpf(object.getCpf());
		newObject.setAddress(object.getAddress());
	}
}
