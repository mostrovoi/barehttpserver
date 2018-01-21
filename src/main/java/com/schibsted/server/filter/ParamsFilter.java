package com.schibsted.server.filter;

import java.io.IOException;
import java.util.Map;

import com.schibsted.server.CustomHttpServerConstants;
import com.schibsted.server.utils.UrlParserUtil;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

public class ParamsFilter extends Filter {

	@Override
	public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {
		Map<String, String> parametersMap = UrlParserUtil.parseUrlParameters(httpExchange.getRequestURI());

		httpExchange.setAttribute(CustomHttpServerConstants.PARAMETERS_ATTRIBUTE, parametersMap);
		chain.doFilter(httpExchange);
	}

	@Override
	public String description() {
		return "Extracts parameters from URL and stores them in a attribute map";
	}

}