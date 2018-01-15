package com.schibsted.server.auth;

import com.schibsted.server.service.UserService;
import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpExchange;

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

    @Override
    public Result authenticate(HttpExchange he) {
        Result result = super.authenticate(he);
        he.getResponseHeaders().remove("WWW-Authenticate");
        return result;
    }
}
