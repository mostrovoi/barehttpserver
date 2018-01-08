package com.schibsted.server.auth;

import com.schibsted.server.service.SessionService;
import com.schibsted.server.service.UserService;
import com.sun.net.httpserver.BasicAuthenticator;

public class CustomBasicAuthenticator extends BasicAuthenticator {
	private final UserService userService;
	private final SessionService sessionService;
	
	public CustomBasicAuthenticator(UserService us, SessionService ss) {
		super("http");
		this.userService = us;
		this.sessionService = ss;
	}

    @Override
    public boolean checkCredentials(String username, String password) {
       
    	if(userService.doLogin(username, password))
    		return sessionService.isValid(username);
    	else
    		return false;
    }

}
