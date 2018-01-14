package com.schibsted.server.domain;

import java.time.Duration;

public class Session {
	
    private final long time;
    private final static long EXPIRATION_TIME = Duration.ofMinutes(5).toMillis();

    private final String token;
    private final String username;

    public Session(String token, String username) {
        this.time = System.currentTimeMillis();
    	this.token = token;
        this.username = username;
    }

    public String getToken() {
        return this.token;
    }

    public String getUsername() {
        return this.username;
    }

    public long getTime() {
        return this.time;
    }

    public long getExpirationTime() {
        return EXPIRATION_TIME;
    }
    
    public boolean isValid() {
        long timeSession = System.currentTimeMillis() - this.getTime() - this.getExpirationTime();
        return (timeSession > 0);
    }
}
