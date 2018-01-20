package com.schibsted.server.filter;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.server.CustomHttpServerConstants;
import com.schibsted.server.service.SessionService;
import com.schibsted.server.utils.CookieUtils;
import com.schibsted.server.utils.HeadersUtil;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

public class SessionFilter extends Filter {

	private SessionService sessionService;
	private static final Logger logger = LogManager.getLogger(SessionFilter.class);

	public SessionFilter(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	@Override
	public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {

		httpExchange.setAttribute(CustomHttpServerConstants.USERNAME_ATTRIBUTE, null);
		httpExchange.setAttribute(CustomHttpServerConstants.SESSION_ATTRIBUTE, null);

		List<String> cookies = httpExchange.getRequestHeaders().get(HeadersUtil.COOKIE_HEADER);
		String sessionId = CookieUtils.getValue(CustomHttpServerConstants.SESSION_KEY, cookies);
		String username = sessionService.getUsername(sessionId);
		if (sessionService.isValid(sessionId)) {
			logger.debug("Session found for username {}", username);
			// TODO: helper
			httpExchange.setAttribute(CustomHttpServerConstants.USERNAME_ATTRIBUTE, username);
			httpExchange.setAttribute(CustomHttpServerConstants.SESSION_ATTRIBUTE, sessionId);
		}

		chain.doFilter(httpExchange);
	}

	@Override
	public String description() {
		return "Session filter that checks for valid session and extracts username into attribute if found";
	}

}