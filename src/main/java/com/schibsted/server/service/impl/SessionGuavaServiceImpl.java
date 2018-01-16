package com.schibsted.server.service.impl;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.schibsted.server.service.SessionService;

public class SessionGuavaServiceImpl implements SessionService {

    public static final int CACHE_SIZE = 1000;
    public static final int SESSION_TIME = 5;
    Cache<String, String> sessions;

    public SessionGuavaServiceImpl() {
        sessions = CacheBuilder.newBuilder()
                .maximumSize(CACHE_SIZE)
                .expireAfterAccess(SESSION_TIME, TimeUnit.MINUTES)
                .build();
    }

    @Override
    public String getUsername(String sessionId) {
        return(sessionId == null)  ? null : sessions.getIfPresent(sessionId);
    }

	@Override
	public String create(String username) {
		String sessionid = generateUniqueToken();
		sessions.put(sessionid, username);
		return sessionid;
	}
	

	@Override
	public void delete(String sessionId) {
		  sessions.invalidate(sessionId);
	}

	@Override
	public boolean isValid(String sessionId) {
		return (this.getUsername(sessionId)!=null);
	}
	

	/*
	 * Generates an 'almost' unique number
	 * @see https://stackoverflow.com/questions/20999792/does-randomuuid-give-a-unique-id
	 */
	private String generateUniqueToken() {
		return UUID.randomUUID().toString();
	}


}
