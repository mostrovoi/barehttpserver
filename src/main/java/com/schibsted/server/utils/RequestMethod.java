package com.schibsted.server.utils;

public enum RequestMethod {
	  GET("GET"), 
	  HEAD("HEAD"), 
	  POST("POST"), 
	  PUT("PUT"), 
	  PATCH("PATCH"),
	  DELETE("DELETE"),
	  OPTIONS("OPTIONS"),
	  TRACE("TRACE");
	
	private final String value;

	RequestMethod(String value) {
		this.value = value;
	}

	/**
	 * Return the string associated to this request method.
	 */
	public String value() {
		return this.value;
	}
}
