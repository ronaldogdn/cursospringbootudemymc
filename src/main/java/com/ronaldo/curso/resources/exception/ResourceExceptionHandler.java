package com.ronaldo.curso.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ronaldo.curso.services.exception.DataIntegrityException;
import com.ronaldo.curso.services.exception.ObjectNotFoundException;

@ControllerAdvice//tratamento global de erros
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandartError> objectNotFound(ObjectNotFoundException exception, HttpServletRequest request){
		StandartError err = new StandartError(HttpStatus.NOT_FOUND.value(), exception.getMessage(), System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandartError> dataIntegrity(DataIntegrityException exception, HttpServletRequest request){
		StandartError err = new StandartError(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandartError> dataIntegrity(MethodArgumentNotValidException exception, HttpServletRequest request){
		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de validação", System.currentTimeMillis());
		
		for(FieldError x : exception.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
}
