package com.schibsted.server.service;

import com.schibsted.server.domain.Session;

public interface SessionService {

	/**
	 * Creates a news session from given username
	 * 
	 * @param username
	 *            the username to be associated with the newly creatd session
	 * 
	 * @return Session new session
	 */
	Session create(String username);

	Session get(String sessionId);

	void delete(String sessionId);

	/**
	 * Checks whether session is still valid and removes it if no longer valid
	 * 
	 * @param sessionId
	 *            the session id to be checked
	 * 
	 * @return true if still valid, false otherwise
	 */
	boolean isValid(String sessionId);

}
