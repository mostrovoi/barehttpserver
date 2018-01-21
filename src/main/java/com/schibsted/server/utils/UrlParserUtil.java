package com.schibsted.server.utils;

import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.server.CustomHttpServerConstants;

public class UrlParserUtil {
	static final Logger logger = LogManager.getLogger(UrlParserUtil.class);
	
	private UrlParserUtil() {
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
		if (query != null) {
			String[] params = query.split("&");
			if (params != null) {
				for (String param : params) {
					String[] split = param.split("=");
					if (split.length == 1)
						parameters.put(split[0], "true");
					else if (split.length == 2)
						parameters.put(split[0], split[1]);
				}
			}
		}
	}

	public static Map<String, String> getFormParametersFromBody(InputStream is) {
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
