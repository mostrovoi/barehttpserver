package com.schibsted.server.view.handler;

import java.io.IOException;

import org.apache.http.HttpStatus;

import com.schibsted.server.service.SessionService;
import com.schibsted.server.utils.HttpServerUtils;
import com.schibsted.server.utils.PageTemplate;
import com.schibsted.server.utils.ViewBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class LogoutHandler implements HttpHandler {
   
	private static final String LOGOUT_TEMPLATE_NAME = "logout.mustache";
	private final SessionService sessionService;

    public LogoutHandler(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void handle(HttpExchange he) throws IOException {
        String username = he.getPrincipal().getUsername();

       // sessionService.delete(username);
        
        String loginForm = ViewBuilder.create(LOGOUT_TEMPLATE_NAME, new PageTemplate("Logout"));
        HttpServerUtils.send(he, loginForm, HttpStatus.SC_OK);
    }
}