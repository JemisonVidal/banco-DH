package com.banco.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.banco.dto.ClientDTO;
import com.banco.model.Client;
import com.banco.service.impl.ClientService;

@RestController
@RequestMapping(value = "/v1/api/client")
public class ClientController {

	@Autowired(required = true)
	private ClientService clientService;

	@Transactional
	@PostMapping
	public ResponseEntity<Void> create(@RequestBody ClientDTO clienteDTO) {
		Client client = clientService.fromDTO(clienteDTO);
		client = this.clientService.create(client);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(client.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Client client) {		
		client.setId(id);
		this.clientService.update(client);
		return ResponseEntity.noContent().build();
	};

	@GetMapping("/{id}")
	public ResponseEntity<Client> findById(@PathVariable Long id) {
		Client client = this.clientService.findById(id);
		return ResponseEntity.ok(client);
	}

	@GetMapping
	public ResponseEntity<List<Client>> findAll() {
		List<Client> cliente = this.clientService.findAll();
		return ResponseEntity.ok(cliente);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.clientService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}/saldo")
	public ResponseEntity<Double> saldoCliente(@PathVariable Long id) {
		Double saldo = this.clientService.saldoCliente(id);
		return ResponseEntity.ok(saldo);
	}

	@PatchMapping("/{id}/sacar/{valorSaque}")
	public ResponseEntity<Double> sacar(@PathVariable Long id, @PathVariable Double valorSaque) {
		Double saldo = this.clientService.sacar(id, valorSaque);
		return ResponseEntity.ok(saldo);
	};

	@PatchMapping("/{id}/depositar/{valorDeposito}")
	public ResponseEntity<Double> depositar(@PathVariable Long id, @PathVariable Double valorDeposito) {
		Double saldo = this.clientService.depositar(id, valorDeposito);
		return ResponseEntity.ok(saldo);
	};

	@PatchMapping("/{idTransferidor}/transferir/{idRecebedor}/{valor}")
	public ResponseEntity<Void> transferir(@PathVariable Long idTransferidor, @PathVariable Long idRecebedor,
			@PathVariable Double valor) {
		this.clientService.transferir(idTransferidor, idRecebedor, valor);

		return ResponseEntity.noContent().build();
	};
}
