package com.ronaldo.curso.controller.exception;

import java.util.ArrayList;
import java.util.List;
/**
 * Validation estende a Classe StandartError
 */
public class ValidationError extends StandartError {
	private static final long serialVersionUID = 1L;

	//lista de erros 
	private List<FieldMessage> errors = new ArrayList<>();
	
	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	//adiciona o erro na lista
	public void addError(String fieldName, String message) {
		this.errors.add(new FieldMessage(fieldName,message));
	}

}
