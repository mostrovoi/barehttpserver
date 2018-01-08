package com.schibsted.server.service.impl;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.schibsted.server.domain.Session;
import com.schibsted.server.service.SessionService;

public class SessionServiceImpl implements SessionService {

    private final ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();
    
   //TODO: check if null
	@Override
	public String create(String username) {
        Session session = new Session(UUID.randomUUID().toString(), username);
        sessionMap.put(username, session);
        return session.getToken();
    }

	@Override
	public String get(String username) {
		Session s = sessionMap.get(username); 
		return (s!=null) ? s.getToken() : null;
	}


	@Override
	public void delete(String username) {
        sessionMap.remove(username);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isValid(String username) {
		boolean result = true;
		Session s = sessionMap.get(username);
		if(s!=null) {
			if (!s.isValid()) {
				this.delete(username);
				result = false;
			}
		} else {
			this.create(username);
		}
		return result;
	}
}
