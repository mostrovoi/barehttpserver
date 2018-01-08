package com.schibsted.server.utils;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;

public enum HttpServerUtils {
	INSTANCE;
	
    public void send(HttpExchange he, String response, int statuscode) throws IOException {
        he.sendResponseHeaders(statuscode, response.length());
        OutputStream os = he.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    
}
