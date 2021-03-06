package com.java.basicApi.testApi.model;

public class ErrorM {
	public enum STATUS{
		SUCCESS,
		FAIL
	}
	
	private STATUS status;
	private String code;
	private String message = "";
	
	public STATUS getStatus() {
		return status;
	}
	public void setStatus(STATUS status) {
		this.status = status;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		if(!this.message.isEmpty()) {
			this.message += ", ";
		}
		this.message += message;
		
	}
}
