package com.ronaldo.curso.domain.enums;

public enum TipoCliente {

	/**
	 * O número é arbitrário, poderia ser qualquer um
	 */
	PESSOAFISICA(1,"Pessoa física"),
	PESSOAJURIDICA(2,"Pessoa jurídica");
	
	private int codigo;
	private String descricao;
	
	private TipoCliente(int cod, String descricao) {
		this.codigo = cod;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoCliente toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		for (TipoCliente x : TipoCliente.values()) {
			if(cod.equals(x.getCodigo())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido: "+cod);
	}
	
}
