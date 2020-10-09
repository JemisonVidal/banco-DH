package com.banco.model.enums;

public enum TypeAccount {

	POUPANCA(1, "Poupança"), 
	CORRENTE(2, "Corrent");	

	private int id;
	private String description;

	private TypeAccount(int id, String description) {
		this.id = id;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public static TypeAccount toEnum(Integer id) {
		if (id == null) {
			return null;
		}

		for (TypeAccount x : TypeAccount.values()) {
			if (id.equals(x.getId())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Id invalid:" + id);
	}

}
