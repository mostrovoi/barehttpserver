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
        sessionMap.put(session.getToken(), session);
        return session.getToken();
    }
//
//	@Override
//	public String get(String username) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	//TODO: return void maybe?
//	@Override
//	public boolean delete(String username) {
//        sessionMap.
//        return true;
//	}
//
//	//TODO: Remove expired sessions
	@Override
	public boolean isValid(String username) {
		Session s = sessionMap.get(sessionId);
		return (s!= null && s.isValid());
	}
}
