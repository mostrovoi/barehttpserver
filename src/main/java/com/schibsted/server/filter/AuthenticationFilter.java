package com.schibsted.server.filter;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.server.CustomHttpServerConstants;
import com.schibsted.server.utils.HeadersUtil;
import com.schibsted.server.utils.HttpExchangeUtil;
import com.schibsted.server.utils.HttpStatus;
import com.schibsted.server.view.dto.PageResponseDTO;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

public class AuthenticationFilter extends Filter {
	
	private final static String ERROR_TEMPLATE_NAME = "error.mustache";

	private final static Logger logger = LogManager.getLogger(AuthenticationFilter.class);

	@Override
	public void doFilter(HttpExchange he, Chain chain) throws IOException {
		if (he.getAttribute(CustomHttpServerConstants.USERNAME_ATTRIBUTE) == null) {
			logger.info("No valid session found. Unauthorized");
	        String errorHtml = HttpExchangeUtil.createHtml(ERROR_TEMPLATE_NAME, new PageResponseDTO("Error","guest","Not authenticated"));
	        HttpExchangeUtil.send(he, errorHtml,HeadersUtil.CONTENT_TYPE_HTML,HttpStatus.UNAUTHORIZED.value());
		} else
			chain.doFilter(he);
	}

	@Override
	public String description() {
		return "Filter that checks whether user is authenticated or not";
	}
}
