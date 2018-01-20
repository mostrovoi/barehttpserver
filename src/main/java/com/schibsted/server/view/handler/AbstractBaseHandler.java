package com.schibsted.server.view.handler;

import static com.danisola.restify.url.RestParserFactory.parser;
import static com.danisola.restify.url.types.StrVar.strVar;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.danisola.restify.url.RestParser;
import com.danisola.restify.url.RestUrl;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.schibsted.server.CustomHttpServerConstants;
import com.schibsted.server.utils.HttpStatus;
import com.schibsted.server.view.dto.PageResponseDTO;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

abstract class AbstractBaseHandler implements HttpHandler {

	final static String ALPHANUMERIC_REGEXP = "^[a-zA-Z0-9]*$";
	static final Logger logger = LogManager.getLogger(AbstractBaseHandler.class);
	
    protected void send(HttpExchange he, String response, int statuscode) throws IOException {
       //TODO: Add content-type html or json
    	he.sendResponseHeaders(statuscode, response.length());
        OutputStream os = he.getResponseBody();
        os.write(response.getBytes());
        os.close();
        he.close();
    }
    
    protected void sendOK(HttpExchange he, String response) throws IOException {
    	this.send(he, response, HttpStatus.OK.value());
    }
    
    protected String createHtml(String templateName, PageResponseDTO pageTemplate) throws IOException {
		MustacheFactory mf = new DefaultMustacheFactory();
		Mustache mustache = mf.compile(templateName);
		StringWriter writer = new StringWriter();
		mustache.execute(writer, pageTemplate).flush();
		return writer.toString();
	}
    

    protected String getLoggedUsername(HttpExchange he) {
    	Object username = he.getAttribute(CustomHttpServerConstants.USERNAME_ATTRIBUTE);
    	return username != null ? (String)username : null;
    }
    
    protected String getCurrentSessionId(HttpExchange he) {
    	Object sessionId = he.getAttribute(CustomHttpServerConstants.SESSION_ATTRIBUTE);
    	return sessionId != null ? (String)sessionId : null;
    }
    
    
    protected String getUsernameFromPath(String url, String context) {
		RestParser parser = parser(context+"/{}", strVar("username",ALPHANUMERIC_REGEXP));
		RestUrl restUrl = parser.parse(url);
		return restUrl.variable("username");
	}
}
