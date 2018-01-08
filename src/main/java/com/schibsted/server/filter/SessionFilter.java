package com.schibsted.server.filter;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpStatus;

import com.schibsted.server.service.SessionService;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

public class SessionFilter extends Filter {

	private final SessionService sessionService;
    private static final String COOKIE_HEADER = "Cookie";
    private static final String LOCATION_HEADER = "Location";
    private static final String SESSION_ID = "SESSIONID";
    private static final String INC_PARAM = "?inc=";
    private static final String EQUAL = "=";
    
    //TODO: Redirect URL, get it from ip + path
    public static final String LOGIN_URL = "http://localhost:8080/login";
    
	public SessionFilter(SessionService ss) {
		this.sessionService = ss;
	}
	
	@Override
    public void doFilter(HttpExchange he, Chain c) throws IOException {
        List<String> cookies = he.getRequestHeaders().get(COOKIE_HEADER);

        String sessionId = getSessionFromCookies(cookies);

        if ( sessionService.isValid(sessionId)) {
            c.doFilter(he);
        } else {
            he.getResponseHeaders().add(LOCATION_HEADER, LOGIN_URL + INC_PARAM + he.getRequestURI());
            he.sendResponseHeaders(HttpStatus.SC_MOVED_TEMPORARILY, -1L);
        }
    }
	
    private String getSessionFromCookies(List<String> cookies) {
        String sessionToken = null;
        if (cookies != null) {
            for (String cookie : cookies) {
                sessionToken = getSessionFromCookie(cookie);
                if (sessionToken != null) break;
            }
        }
        return sessionToken;
    }

    private String getSessionFromCookie(String cookie) {
        String sessionId = null;
        if (cookie != null) {
            int index = cookie.indexOf(EQUAL);
            if (index > 0) {
                String key = cookie.substring(0, index);
                if (SESSION_ID.equals(key)) {
                	sessionId = cookie.substring(index + 1);
                }
            }
        }
        return sessionId;
    }

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return null;
	}


}
