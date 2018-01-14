package com.schibsted.server.view.rest;

public class APIResponseDTO {

	private int statusCode;
	private String body;
	
	public APIResponseDTO(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public APIResponseDTO(int statusCode, String body) {
		this(statusCode);
		this.body = body;
	}
	
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}

}
