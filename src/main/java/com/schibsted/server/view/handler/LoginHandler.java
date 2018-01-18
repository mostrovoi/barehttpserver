package com.schibsted.server.view.handler;

import java.io.IOException;

import com.schibsted.server.CustomHttpServer;
import com.schibsted.server.utils.HttpExchangeUtil;
import com.schibsted.server.utils.HttpStatus;
import com.schibsted.server.view.dto.PageResponseDTO;
import com.sun.net.httpserver.HttpExchange;

public class LoginHandler extends AbstractBaseHandler {

	private static final String FORM_LOGIN_TEMPLATE_NAME = "login.mustache";
	
	//TODO: Make it global when moving to helper
	public static final String LOCATION_HEADER = "Location";
	

	@Override
	public void handle(HttpExchange he) throws IOException {
		//3 cases -> First landing, Wrong credentials and got valid credentials 
	    logger.debug("dddf: {}",he.getRequestURI()	);
		String username = getLoggedUsername(he);
		if(username == null) {
			String errorMsg = "";
			if("1".equals(he.getAttribute(CustomHttpServer.LOGIN_ERROR_ATTRIBUTE)))
				errorMsg =  "Wrong username or password";
	    	String loginForm = createHtml(FORM_LOGIN_TEMPLATE_NAME, new PageResponseDTO("Login", "",errorMsg));
	    	sendOK(he,loginForm);
		}
		else {
			String sessionId = getCurrentSessionId(he);
		    he.getResponseHeaders().add("Set-Cookie",CustomHttpServer.SESSION_KEY+"="+sessionId);		    
		    String responseUrl = HttpExchangeUtil.parseRequestQuery("inc", he.getRequestURI().toString());
		    logger.debug("dddf: {}",he.getRequestURI().toString());
		    logger.debug("dddf: {}",he.getRequestURI()	);
		    
		    he.getResponseHeaders().add(LOCATION_HEADER, "private");
		    he.sendResponseHeaders(HttpStatus.SEE_OTHER.value(), -1L);
		    he.close();
		}
	}
}