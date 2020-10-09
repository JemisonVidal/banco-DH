package com.banco.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.banco.dto.AccountDTO;
import com.banco.model.Account;
import com.banco.model.enums.TypeAccount;
import com.banco.repository.AccountRepository;
import com.banco.service.IService;
import com.banco.service.exceptions.DataIntegrityException;
import com.banco.service.exceptions.ObjectNotFoundException;

@Service
public class AccountService implements IService<Account> {

	@Autowired
	private AccountRepository contaRepository;

	@Override
	public Account create(Account object) {
		object.setId(null);
		object = contaRepository.save(object);
		return object;
	}

	@Override
	public Account findById(Long id) {
		Optional<Account> obj = contaRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Object not found! Id: " + id + ", type: " + Account.class.getName()));
	}

	@Override
	public List<Account> findAll() {
		return this.contaRepository.findAll();
	}

	@Override
	public Account update(Account object) {
		Account newObject = this.findById(object.getId());
		this.updateData(newObject, object);
		return this.contaRepository.save(newObject);
	}

	@Override
	public void delete(Long id) {
		this.findById(id);
		try {
			this.contaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("It was not possible to delete the profile");
		}
	}

	public Double saldoConta(Long id) {
		Account conta = this.findById(id);

		return conta.getSaldo();
	}

	public Double sacar(Long id, Double valor) {
		Account conta = this.findById(id);
		conta.setSaldo(conta.getSaldo() - valor);
		this.contaRepository.save(conta);
		return conta.getSaldo();
	}

	public Double depositar(Long id, Double valor) {
		Account conta = this.findById(id);
		conta.setSaldo(conta.getSaldo() + valor);
		this.contaRepository.save(conta);
		return conta.getSaldo();
	}

	@Transactional
	public void transferir(Long idTransferidor, Long idRecebedor, Double valor) {
		Account contaTransferidor = this.findById(idTransferidor);
		Account contaRecebedor = this.findById(idRecebedor);
		contaTransferidor.setSaldo(contaTransferidor.getSaldo() - valor);
		contaRecebedor.setSaldo(contaRecebedor.getSaldo() + valor);
		this.contaRepository.save(contaTransferidor);
		this.contaRepository.save(contaRecebedor);
	}

	public Account fromDTO(AccountDTO contaDTO) {
		return new Account(contaDTO.getId(), contaDTO.getSaldo(), contaDTO.getSpecialLimit(),
				TypeAccount.toEnum(contaDTO.getTypeAccount()));
	}

	private void updateData(Account newObject, Account object) {
		newObject.setId(object.getId());
		newObject.setSaldo(object.getSaldo());
		newObject.setSpecialLimit(object.getSpecialLimit());
		newObject.setTypeAccount(object.getTypeAccount());
	}
}
