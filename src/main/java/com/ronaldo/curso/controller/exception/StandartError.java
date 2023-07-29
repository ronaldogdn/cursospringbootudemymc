package com.ronaldo.curso.controller.exception;

import java.io.Serializable;

public class StandartError implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer status;
	private String msg;
	private Long TimeStamp;
	
	public StandartError(Integer status, String msg, Long timeStamp) {
		super();
		this.status = status;
		this.msg = msg;
		TimeStamp = timeStamp;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Long getTimeStamp() {
		return TimeStamp;
	}
	public void setTimeStamp(Long timeStamp) {
		TimeStamp = timeStamp;
	}
}