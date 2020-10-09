package com.banco.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.banco.dto.GerenteDTO;
import com.banco.model.Client;
import com.banco.model.Gerente;
import com.banco.repository.GerenteRepository;
import com.banco.service.IService;
import com.banco.service.exceptions.DataIntegrityException;
import com.banco.service.exceptions.ObjectNotFoundException;

@Service
public class GerenteService implements IService<Gerente> {

	@Autowired
	private GerenteRepository gerenteRepository;

	@Override
	public Gerente create(Gerente object) {
		object.setId(null);
		object = gerenteRepository.save(object);
		return object;
	}

	@Override
	public Gerente findById(Long id) {
		Optional<Gerente> obj = gerenteRepository.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Object not found! Id: " + id + ", type: " + Client.class.getName()));
	}

	@Override
	public List<Gerente> findAll() {
		return this.gerenteRepository.findAll();
	}

	@Override
	public Gerente update(Gerente object) {
		Gerente newObject = this.findById(object.getId());
		this.updateData(newObject, object);
		return this.gerenteRepository.save(newObject);
	}

	@Override
	public void delete(Long id) {
		this.findById(id);
		try {
			this.gerenteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("It was not possible to delete the profile");
		}
	}

	public Gerente fromDTO(GerenteDTO gerenteDTO) {
		return new Gerente(gerenteDTO.getId(), gerenteDTO.getName(), gerenteDTO.getCpf(), gerenteDTO.getAddress());
	}

	private void updateData(Gerente newObject, Gerente object) {
		newObject.setId(object.getId());
		newObject.setName(object.getName());
		newObject.setCpf(object.getCpf());
		newObject.setAddress(object.getAddress());
	}
}
