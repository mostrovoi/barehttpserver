package com.schibsted.server.utils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.server.CustomHttpServerConstants;

public class UrlParserUtils {
	static final Logger logger = LogManager.getLogger(UrlParserUtils.class);
	
	private UrlParserUtils() {
	}

	public static Map<String, String> parseUrlParameters(URI uri) {

		Map<String, String> parameters = new HashMap<>();

		String path = uri.getPath();
		String queryString = uri.getQuery();
		parseAPIUrl(path, parameters);
		parseQueryParams(queryString, parameters);
		return parameters;
	}

	public static void parseAPIUrl(String path, Map<String, String> parameters) {
		if (path != null) {
			String paths[] = path.split("/");
			for (int j = 1; j < paths.length; j++) {
				parameters.put(CustomHttpServerConstants.PATHLEVEL_ATTRIBUTE + j, paths[j]);
			}
		}
	}

	public static void parseQueryParams(String query, Map<String, String> parameters) {
		if (query != null && query.length() != 0) {
			String[] params = query.split("&");
			if (params != null) {
				for (String param : params) {
					String[] split = param.split("=");
					if (split.length == 1)
						parameters.put(split[0], "");
					else if (split.length == 2) {
						String value = split[1];
						try {
						   value = URLDecoder.decode(split[1],StreamUtils.UTF_8);
						} catch (UnsupportedEncodingException e) {
							logger.error("Error while decoding value {}",split[1]);
						}
						parameters.put(split[0], value);
					}
				}
			}
		}
	}
	
	public static Map<String,String> getFormParametersFromBody(String query) {
		Map<String,String> params = new HashMap<>();
		parseQueryParams(query,params);
		return params;
	}

}
