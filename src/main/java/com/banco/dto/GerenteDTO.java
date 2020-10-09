package com.banco.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

import com.banco.model.Gerente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GerenteDTO implements Serializable {
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

	public GerenteDTO(Gerente gerente) {
		this.id = gerente.getId();
		this.name = gerente.getName();
		this.cpf = gerente.getCpf();
		this.address = gerente.getAddress();

	}
}
