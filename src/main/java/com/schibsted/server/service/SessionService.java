package com.schibsted.server.service;

public interface SessionService {

	/**
	 * Creates a news session from given username
	 * 
	 * @param username
	 *            the username to be associated with the newly creatd session
	 * 
	 * @return String new sessionId
	 */
	String create(String username);

	/**
	 * Gets the username for the given session if exists
	 * 
	 * @param sessionId
	 *            session to be looked up
	 * @return username if found, null otherwise
	 */
	String getUsername(String sessionId);

	/**
	 * Invalidates the session associated to this sessionId if exists
	 * 
	 * @param sessionId
	 *            the session to be removed
	 */
	void delete(String sessionId);

	/**
	 * Checks whether session is still valid, i.e. has associated user
	 * 
	 * @param sessionId
	 *            the session id to be checked
	 * 
	 * @return true if still valid, false otherwise
	 */
	boolean isValid(String sessionId);

}
