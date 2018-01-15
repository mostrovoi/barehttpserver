package com.schibsted.server.auth;

import com.schibsted.server.service.UserService;
import com.sun.net.httpserver.BasicAuthenticator;

public class CustomAuthenticator extends BasicAuthenticator {
	private final UserService userService;
	
	public CustomAuthenticator(UserService us) {
		super("schibsted");
		this.userService = us;
	}

    @Override
    public boolean checkCredentials(String username, String password) {
        return userService.checkCredentials(username, password);
    }
}
