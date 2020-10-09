package com.banco.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

import com.banco.model.Account;
import com.banco.model.Client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;

	@NotBlank(message = "Mandatory Filling")
	@Column(length = 50, nullable = false)
	private String name;

	@NotBlank(message = "Mandatory Filling")
	@Column(length = 14, nullable = false)
	private String cpf;

	@NotBlank(message = "Mandatory Filling")
	@Column(length = 50, nullable = false)
	private String address;
	
	@NotBlank(message = "Mandatory Filling")
	private Account account;

	public ClientDTO(Client client) {
		this.id = client.getId();
		this.name = client.getName();
		this.cpf = client.getCpf();
		this.address = client.getAddress();
		this.account = client.getAccount();

	}
}
