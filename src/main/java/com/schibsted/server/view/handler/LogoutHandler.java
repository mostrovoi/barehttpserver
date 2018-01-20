package com.schibsted.server.view.handler;

import java.io.IOException;

import com.schibsted.server.CustomHttpServerConstants;
import com.schibsted.server.service.SessionService;
import com.schibsted.server.utils.CookieUtils;
import com.schibsted.server.utils.HeadersUtil;
import com.schibsted.server.view.dto.PageResponseDTO;
import com.sun.net.httpserver.HttpExchange;

public class LogoutHandler extends AbstractBaseHandler {

	private final static String LOGOUT_TEMPLATE_NAME = "logout.mustache";
	private final SessionService sessionService;

	public LogoutHandler(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	@Override
	public void handle(HttpExchange he) throws IOException {
		String sessionId = this.getCurrentSessionId(he);
		String username =  this.getLoggedUsername(he);
		logger.info("User {} with sessionId {} has been logged out",username,sessionId);
		//Removes the cookie in both browser and server
		sessionService.delete(sessionId);
		//TODO: move to helper
	    he.getResponseHeaders().add(HeadersUtil.SET_COOKIE_HEADER,CookieUtils.getSetDeletedSession(CustomHttpServerConstants.SESSION_KEY));
		String loginForm = createHtml(LOGOUT_TEMPLATE_NAME, new PageResponseDTO("Logout"));
		sendOK(he, loginForm);
	}
}