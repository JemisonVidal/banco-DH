package com.banco.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

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

import com.banco.dto.AccountDTO;
import com.banco.model.Account;
import com.banco.service.impl.AccountService;

@RestController
@RequestMapping(value = "/v1/api/account")
public class AccountController {

	@Autowired(required = true)
	private AccountService contaService;
	
//	@PostMapping
//	public ResponseEntity<Void> create(@Valid @RequestBody AccountDTO contaDTO) {
//		Account conta = contaService.fromDTO(contaDTO);
//		contaService.create(conta);
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(conta.getId())
//				.toUri();
//		return ResponseEntity.created(uri).build();
//	}	

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Account conta) {
		conta.setId(id);
		this.contaService.update(conta);
		return ResponseEntity.noContent().build();
	};

	@GetMapping("/{id}")
	public ResponseEntity<Account> findById(@PathVariable Long id) {
		Account conta = this.contaService.findById(id);
		return ResponseEntity.ok(conta);
	}

	@GetMapping
	public ResponseEntity<List<Account>> findAll() {
		List<Account> contas = this.contaService.findAll();
		return ResponseEntity.ok(contas);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.contaService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}/saldo")
	public ResponseEntity<Double> saldoConta(@PathVariable Long id) {
		Double saldo = this.contaService.saldoConta(id);
		return ResponseEntity.ok(saldo);
	}

	@PatchMapping("/{id}/sacar/{valorSaque}")
	public ResponseEntity<Double> sacar(@PathVariable Long id, @PathVariable Double valorSaque) {
		Double saldo = this.contaService.sacar(id, valorSaque);
		return ResponseEntity.ok(saldo);
	};

	@PatchMapping("/{id}/depositar/{valorDeposito}")
	public ResponseEntity<Double> depositar(@PathVariable Long id, @PathVariable Double valorDeposito) {
		Double saldo = this.contaService.depositar(id, valorDeposito);
		return ResponseEntity.ok(saldo);
	};

	@PatchMapping("/{idTransferidor}/transferir/{idRecebedor}/{valor}")
	public ResponseEntity<Void> transferir(@PathVariable Long idTransferidor, @PathVariable Long idRecebedor,
			@PathVariable Double valor) {
		this.contaService.transferir(idTransferidor, idRecebedor, valor);

		return ResponseEntity.noContent().build();
	};
}
