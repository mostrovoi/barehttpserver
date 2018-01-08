package com.schibsted.server.service;

public interface SessionService {

	String create(String username);

	//String get(String username);

	//boolean delete(String username);
	
	boolean isValid(String username);

}
