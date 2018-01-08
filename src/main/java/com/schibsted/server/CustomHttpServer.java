package com.schibsted.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class CustomHttpServer {

	private final HttpServer server;
    private static final int PORT = 8080;
    
    public CustomHttpServer() throws IOException {
    	server = HttpServer.create(new InetSocketAddress(PORT), 0);
    }
    
	public void start() throws IOException {
		server.start();
	}
	
	public HttpContext createContext(String path, HttpHandler httpHandler){
		return server.createContext(path,httpHandler);
	}
	
	public HttpContext createContextWithFilter(String path, HttpHandler httpHandler, Filter filter){
		HttpContext ctx = server.createContext(path,httpHandler);
        if (filter != null) 
            ctx.getFilters().add(filter);
        return ctx;
	}
}