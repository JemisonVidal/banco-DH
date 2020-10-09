package com.banco.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.banco.model.enums.TypeAccount;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private double saldo;
	private double specialLimit;
	private Integer typeAccount;
	
	@JsonBackReference
	@OneToOne	
	@JoinColumn(name = "client_id")
	private Client client;

	public Account(Long id, double saldo, double specialLimit, TypeAccount typeAccount) {
		super();
		this.id = id;
		this.saldo = saldo;
		this.specialLimit = specialLimit;
		this.typeAccount = typeAccount.getId();		
	}
	
}
