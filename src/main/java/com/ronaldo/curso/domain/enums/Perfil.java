package com.ronaldo.curso.domain.enums;

public enum Perfil {

	ADMIN(1,"ADMIN"),
	CLIENTE(2,"CLIENTE");
	
	private int codigo;
	private String descricao;
	
	private Perfil(int cod, String descricao) {
		this.codigo = cod;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Perfil toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		for (Perfil x : Perfil.values()) {
			if(cod.equals(x.getCodigo())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido: "+cod);
	}
}
