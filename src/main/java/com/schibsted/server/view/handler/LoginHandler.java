package com.schibsted.server.view.handler;

import java.io.IOException;

import com.schibsted.server.CustomHttpServer;
import com.schibsted.server.domain.Session;
import com.schibsted.server.service.SessionService;
import com.sun.net.httpserver.HttpExchange;

public class LoginHandler extends AbstractBaseHandler {

	private final SessionService sessionService;

	public LoginHandler(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	@Override
	public void handle(HttpExchange he) throws IOException {
		String username = he.getPrincipal().getUsername();
		logger.info("User {} authenticated", username);
	    Session session = sessionService.create(username);
	   
	    he.getResponseHeaders().add("Set-Cookie",CustomHttpServer.SESSION_KEY+"="+session.getToken());
	    String response = "{}";
	    sendOK(he, response);
	}
}