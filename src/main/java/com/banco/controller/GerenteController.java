package com.banco.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.banco.dto.GerenteDTO;
import com.banco.model.Gerente;
import com.banco.service.impl.GerenteService;

@RestController
@RequestMapping(value = "/v1/api/gerente")
public class GerenteController {

	@Autowired(required = true)
	private GerenteService gerenteService;

	@GetMapping("/{id}")
	public ResponseEntity<Gerente> findById(@PathVariable Long id) {
		Gerente gerente = this.gerenteService.findById(id);

		return ResponseEntity.ok(gerente);
	}

	@PostMapping
	public ResponseEntity<Void> create(@Valid @RequestBody GerenteDTO gerenteDTO) {
		Gerente gerente = gerenteService.fromDTO(gerenteDTO);
		gerente = this.gerenteService.create(gerente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(gerente.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Gerente gerente) {
		gerente.setId(id);
		this.gerenteService.update(gerente);
		return ResponseEntity.noContent().build();
	};

	@GetMapping
	public ResponseEntity<List<Gerente>> findAll() {
		List<Gerente> gerentes = this.gerenteService.findAll();
		return ResponseEntity.ok(gerentes);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.gerenteService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
