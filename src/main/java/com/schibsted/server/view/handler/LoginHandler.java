package com.schibsted.server.view.handler;

import java.io.IOException;

import org.apache.http.HttpStatus;

import com.schibsted.server.service.SessionService;
import com.schibsted.server.utils.HttpServerUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class LoginHandler implements HttpHandler {

	private final SessionService sessionService;
	
	public LoginHandler(SessionService ss) {
		this.sessionService = ss;
	}
	
	@Override
	public void handle(HttpExchange he) throws IOException {
        String username = he.getPrincipal().getName();
        String sessionId = sessionService.create(username);
        String response = "{\"sessionToken\": \"" + sessionId + "\"}";
        HttpServerUtils.INSTANCE.send(he, response, HttpStatus.SC_OK);
    }
}
