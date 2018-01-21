package com.schibsted.server.view.handler;

import java.io.IOException;

import com.schibsted.server.CustomHttpServerConstants;
import com.schibsted.server.utils.HeadersUtil;
import com.schibsted.server.utils.HttpExchangeUtil;
import com.schibsted.server.utils.HttpStatus;
import com.schibsted.server.view.dto.PageResponseDTO;
import com.sun.net.httpserver.HttpExchange;

public class LoginHandler extends AbstractBaseHandler {

	private static final String FORM_LOGIN_TEMPLATE_NAME = "login.mustache";
	
	@Override
	public void handle(HttpExchange he) throws IOException {
		//3 cases -> First landing, Wrong credentials and got valid credentials 
		String username = HttpExchangeUtil.getLoggedUsername(he);
		if(username == null) {
			String errorMsg = "";
			if("1".equals(he.getAttribute(CustomHttpServerConstants.LOGIN_ERROR_ATTRIBUTE)))
				errorMsg =  "Wrong username or password";
	    	String loginForm = HttpExchangeUtil.createHtml(FORM_LOGIN_TEMPLATE_NAME, new PageResponseDTO("Login", "",errorMsg));
	    	HttpExchangeUtil.sendHtmlOK(he,loginForm);
		}
		else {
			String sessionId = HttpExchangeUtil.getCurrentSessionId(he);
		    he.getResponseHeaders().add(HeadersUtil.SET_COOKIE_HEADER,CustomHttpServerConstants.SESSION_KEY+"="+sessionId);		    
		    he.getResponseHeaders().add(HeadersUtil.LOCATION_HEADER, "private");
		    he.sendResponseHeaders(HttpStatus.SEE_OTHER.value(), -1L);
		    he.close();
		}
	}
}