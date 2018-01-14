package com.schibsted.server.utils;

import static com.danisola.restify.url.RestParserFactory.parser;
import static com.danisola.restify.url.types.StrVar.strVar;

import com.danisola.restify.url.RestParser;
import com.danisola.restify.url.RestUrl;

public class ApiUtils {
	

	private final static String ALPHANUMERIC_REGEXP = "^[a-zA-Z0-9]*$";
	
	private ApiUtils() {}
	
    
	public static String getUsernameFromPath(String url, String context) {
		RestParser parser = parser(context+"/{}", strVar("username",ALPHANUMERIC_REGEXP));
		RestUrl restUrl = parser.parse(url);
		return restUrl.variable("username");
	}
	
	public static String convertStreamToString(java.io.InputStream is, String charset) {
	    java.util.Scanner s = new java.util.Scanner(is,charset).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
}
