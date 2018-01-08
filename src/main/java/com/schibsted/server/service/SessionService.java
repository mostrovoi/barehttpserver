package com.schibsted.server.service;

public interface SessionService {

	String create(String username);

	String get(String username);

	void delete(String username);
	
	/**
	 * Checks whether a session for a given username has expired in case it exists.
	 * If the session is no longer valid, gets removed
	 * If the session is not found, creates a new session for the user
	 * There is 1-to-1  mapping username-valid session 
	 * 
	 * @param username attached to the session.
	 * @return true if session was not created or has not expired yet, false if session is found and expired
	 */
	boolean isValid(String username);

}
