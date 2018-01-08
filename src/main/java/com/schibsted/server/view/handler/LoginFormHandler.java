package com.schibsted.server.view.handler;

import java.io.IOException;

import org.apache.http.HttpStatus;

import com.schibsted.server.utils.HttpServerUtils;
import com.schibsted.server.utils.PageTemplate;
import com.schibsted.server.utils.ViewBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Basic handler that returns a login form prior to authentication
 * 
 * @author operezdo
 *
 */
public class LoginFormHandler implements HttpHandler {

	private static final String LOGIN_TEMPLATE_NAME = "login.mustache";
	
	@Override
	public void handle(HttpExchange he) throws IOException {
		
        String loginForm = ViewBuilder.create(LOGIN_TEMPLATE_NAME, new PageTemplate("Login"));
        HttpServerUtils.INSTANCE.send(he, loginForm, HttpStatus.SC_OK);
	}

}
