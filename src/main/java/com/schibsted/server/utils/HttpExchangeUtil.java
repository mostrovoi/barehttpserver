package com.schibsted.server.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpExchangeUtil {

	private HttpExchangeUtil() {
	}

	/**
	 * Helper method that extract a given parameter from the URI
	 * 
	 * @param parameter
	 *            parameter to be looked up
	 * @param query
	 *            the entire query of the uri to be parsed
	 * @return the value for given parameter, null if not found
	 */
	public static String parseRequestQuery(String parameter, String query) {
		if(parameter == null || query == null)
			return null;
		
		String [] params = query.split("&");
		if(params != null){
			for(String param : params) {
				String [] split = param.split("=");
				if(parameter.equals(split[0]))
					return split[1];
			}
		}
		return null;
	}

	public static Map<String, String> getFormParametersFromBody(InputStream is) {
		// FIXME: Dont take content-type for granted: It should be Content-Type:
		// application/x-www-form-urlencoded;
		// SEE:
		// https://www.w3.org/TR/html5/sec-forms.html#application-x-www-form-urlencoded-encoding-algorithm
		Map<String, String> params = new HashMap<>();
		String input = StreamUtils.convertInputStreamToString(is, StreamUtils.UTF_8);

		Pattern p = Pattern.compile("(?:(\\w*)=(\\w*)(?=&|$))");
		Matcher m = p.matcher(input);

		while (m.find()) {
			params.put(m.group(1), m.group(2));
		}
		return (params.isEmpty()) ? null : params;
	}

}
