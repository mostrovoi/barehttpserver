package com.schibsted.server.view.handler;

import java.io.IOException;

import com.schibsted.server.service.UserService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ApiHandler implements HttpHandler {

	private final UserService userService;
	
	public ApiHandler(UserService us) {
		this.userService = us;
	}
	
	@Override
	public void handle(HttpExchange arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
