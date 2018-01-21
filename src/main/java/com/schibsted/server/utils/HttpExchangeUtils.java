package com.schibsted.server.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.schibsted.server.CustomHttpServerConstants;
import com.schibsted.server.view.dto.PageResponseDTO;
import com.sun.net.httpserver.HttpExchange;

public class HttpExchangeUtils {
	
	private HttpExchangeUtils() {}
	
    public static void send(HttpExchange he, String response, String contentType, int statuscode) throws IOException {
    	he.getResponseHeaders().add(HeadersUtila.CONTENT_TYPE_HEADER,contentType);
    	if(response == null || response.length() == 0) {
    		he.sendResponseHeaders(statuscode,-1L);
    		he.close();
    	}
    	else {
	    	he.sendResponseHeaders(statuscode, response.length());
	        OutputStream os = he.getResponseBody();
	        os.write(response.getBytes());
	        os.close();
    	}
    }
    
    public static void sendHtmlOK(HttpExchange he, String response) throws IOException {
    	send(he, response, HeadersUtila.CONTENT_TYPE_HTML, HttpStatus.OK.value());
    }
    
    public static String createHtml(String templateName, PageResponseDTO pageTemplate) throws IOException {
		MustacheFactory mf = new DefaultMustacheFactory();
		Mustache mustache = mf.compile(templateName);
		StringWriter writer = new StringWriter();
		mustache.execute(writer, pageTemplate).flush();
		return writer.toString();
	}
    

    public static String getLoggedUsername(HttpExchange he) {
    	Object username = he.getAttribute(CustomHttpServerConstants.USERNAME_ATTRIBUTE);
    	return username != null ? (String)username : null;
    }
    
    public static String getCurrentSessionId(HttpExchange he) {
    	Object sessionId = he.getAttribute(CustomHttpServerConstants.SESSION_ATTRIBUTE);
    	return sessionId != null ? (String)sessionId : null;
    }
}
