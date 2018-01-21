package com.schibsted.server.filter;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.server.CustomHttpServerConstants;
import com.schibsted.server.utils.HeadersUtils;
import com.schibsted.server.utils.HttpStatus;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

public class AuthenticationFilter extends Filter {
	
	private static final Logger logger = LogManager.getLogger(AuthenticationFilter.class);

	private final String redirectUrl;
	
	public AuthenticationFilter(String loginUrl) {
		this.redirectUrl = loginUrl; 
	}
	@Override
	public void doFilter(HttpExchange he, Chain chain) throws IOException {
		if (he.getAttribute(CustomHttpServerConstants.USERNAME_ATTRIBUTE) == null) {
			logger.info("No valid session found. Redirecting to login");
		    he.getResponseHeaders().add(HeadersUtils.LOCATION_HEADER, redirectUrl + "?" + CustomHttpServerConstants.LOCATION_PARAMETER + "=" + he.getRequestURI());
		    he.sendResponseHeaders(HttpStatus.SEE_OTHER.value(), -1L);
		    he.close();
		} else
			chain.doFilter(he);
	}

	@Override
	public String description() {
		return "Filter that checks whether user is authenticated or not";
	}
}
