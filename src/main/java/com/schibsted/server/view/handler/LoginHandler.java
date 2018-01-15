package com.schibsted.server.view.handler;

import java.io.IOException;

import com.schibsted.server.CustomHttpServer;
import com.schibsted.server.service.SessionService;
import com.schibsted.server.utils.HttpStatus;
import com.schibsted.server.view.dto.PageResponseDTO;
import com.sun.net.httpserver.HttpExchange;

public class LoginHandler extends AbstractBaseHandler {

	private static final String FORM_LOGIN_TEMPLATE_NAME = "login.mustache";
	
	//TODO: Make it global
	public static final String LOCATION_HEADER = "Location";
	

	@Override
	public void handle(HttpExchange he) throws IOException {
		//TODO: 3 cases -> First landing, Wrong credentials and got valid credentials 
		logger.info("HELLO1.1");
		String username = getLoggedUsername(he);
		logger.info("HELLO1.1");
		if(username == null) {
			logger.info("HELLO2");
    		String loginForm = createHtml(FORM_LOGIN_TEMPLATE_NAME, new PageResponseDTO("Login", "", "Wrong username or password"));
            //TODO : find out whether send 200 or 401
    		//send(he, loginForm, HttpStatus.UNAUTHORIZED.value());
    		sendOK(he,loginForm);
		}
		else {
			String sessionId = getCurrentSessionId(he);
			logger.info("HELLO3: {}",username);
			logger.info("HELLO4: {}",sessionId);
		    he.getResponseHeaders().add("Set-Cookie",CustomHttpServer.SESSION_KEY+"="+sessionId);
		    //TODO: Get URI from request Uri and send accordingly either to index or url
		    //String response = "{}";
		    he.getResponseHeaders().add(LOCATION_HEADER, "index");
		    he.sendResponseHeaders(HttpStatus.SEE_OTHER.value(), -1L);
		}
	}
}