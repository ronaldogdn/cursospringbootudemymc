package com.ronaldo.curso.controller.exception;

import java.util.ArrayList;
import java.util.List;
/**
 * Validation estende a Classe StandardError
 */
public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;

	//lista de erros 
	private List<FieldMessage> errors = new ArrayList<>();
	
	public ValidationError(Long timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	//adiciona o erro na lista
	public void addError(String fieldName, String message) {
		this.errors.add(new FieldMessage(fieldName,message));
	}

}
