package com.schibsted.server.service.impl;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.schibsted.server.domain.Session;
import com.schibsted.server.service.SessionService;

public class SessionServiceImpl implements SessionService {

    private final ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();
    
	@Override
	public Session create(String username) {
		String sessionId = generateUniqueToken();
        Session session = new Session(sessionId, username);
        sessionMap.put(sessionId, session);
        return session;
    }

	@Override
	public Session get(String sessionId) {
		return (sessionId == null) ? null : sessionMap.get(sessionId); 
	}

	@Override
	public void delete(String sessionId) {
        sessionMap.remove(sessionId);
	}

	@Override
	public boolean isValid(String sessionId) {
		Session s = sessionMap.get(sessionId);
		if (s != null && s.isValid())
			return true;
		else {
			this.delete(sessionId);
			return false;
		}
	}
	
	/*
	 * Generates an 'almost' unique number
	 * @see https://stackoverflow.com/questions/20999792/does-randomuuid-give-a-unique-id
	 */
	private String generateUniqueToken() {
		return UUID.randomUUID().toString();
	}
}
