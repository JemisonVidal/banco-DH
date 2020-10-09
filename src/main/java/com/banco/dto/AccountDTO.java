package com.banco.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

import com.banco.model.Account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@Column(length = 20, nullable = false)
	private double saldo;
	
	@Column(length = 20, nullable = false)
	private double specialLimit;	
	
	@NotBlank(message = "Mandatory Filling")
	private Integer typeAccount;
	
//	@NotBlank(message = "Mandatory Filling")
//	private Client client;

	public AccountDTO(Account conta) {
		this.id = conta.getId();
		this.saldo = conta.getSaldo();
		this.specialLimit = conta.getSpecialLimit();
		this.typeAccount = conta.getTypeAccount();
//		this.client = conta.getClient();
	}
}
