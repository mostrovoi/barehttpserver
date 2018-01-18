package com.schibsted.server.filter;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.server.CustomHttpServer;
import com.schibsted.server.utils.HttpStatus;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

public class RedirectFilter extends Filter {

	// TODO: Make it global
	public static final String LOCATION_HEADER = "Location";
	public static final String INC_PARAM = "?inc=";

	private final String loginUrl;

	private static final Logger logger = LogManager.getLogger(RedirectFilter.class);

	public RedirectFilter(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	@Override
	public void doFilter(HttpExchange he, Chain chain) throws IOException {
		if (he.getAttribute(CustomHttpServer.USERNAME_ATTRIBUTE) == null) {
			logger.info("No valid session found. Redirecting to login page");
			he.getResponseHeaders().add(LOCATION_HEADER, loginUrl + INC_PARAM + he.getRequestURI());
			he.sendResponseHeaders(HttpStatus.SEE_OTHER.value(), -1L);
			he.close();
		} else
			chain.doFilter(he);
	}

	@Override
	public String description() {
		return "Filter that redirects the petition to login url if no valid session is found";
	}
}
