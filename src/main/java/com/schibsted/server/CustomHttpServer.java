package com.schibsted.server;

import java.io.IOException;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.schibsted.server.CustomHttpServerConstants;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class CustomHttpServer {

	private final HttpServer server;

    public CustomHttpServer() throws IOException {
    	server = HttpServer.create(new InetSocketAddress(CustomHttpServerConstants.PORT), 0);
    }
    
	public void start() throws IOException {
		server.start();
	}
	
	public HttpContext createContext(String path, HttpHandler httpHandler){
		return server.createContext(path,httpHandler);
	}
	
	public String getUrl() {
		return CustomHttpServerConstants.URL+CustomHttpServerConstants.PORT;
	}
	
	public String getHost() {
		return CustomHttpServerConstants.HOST+CustomHttpServerConstants.PORT;
	}

	public HttpContext createContextWithFilters(String path, HttpHandler httpHandler, Filter... filter) {
		HttpContext context = server.createContext(path, httpHandler);
		if (filter != null) {
	        List<Filter> filters = new ArrayList<>();
	        filters.addAll(Arrays.asList(filter));
			context.getFilters().addAll(filters);
		}

		return context;
	}
}