package com.ronaldo.curso.services.exception;

public class ObjectNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	/**
	 * Recebe a mensagem de erro da camada Services
	 * @param msg mensagem a ser recebida
	 */
	public ObjectNotFoundException(String msg) {
		super(msg);
	}
	
	public ObjectNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
