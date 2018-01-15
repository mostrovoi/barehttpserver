package com.schibsted.server.filter;

import java.io.IOException; 
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.server.CustomHttpServer;
import com.schibsted.server.domain.Session;
import com.schibsted.server.service.SessionService;
import com.schibsted.server.utils.HttpStatus;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

public class SessionFilter extends Filter {

    public static final String COOKIE_HEADER = "Cookie";
    public static final String LOCATION_HEADER = "Location";
    public static final String INC_PARAM = "?inc=";
    
    private SessionService sessionService;
    private final String loginUrl;

	private static final Logger logger = LogManager.getLogger(SessionFilter.class);
	
    public SessionFilter(SessionService sessionService, String loginUrl) {
        this.sessionService = sessionService;
        this.loginUrl = loginUrl;
    }

    @Override
    public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {
        List<String> cookies = httpExchange.getRequestHeaders().get(COOKIE_HEADER);
        String sessionToken = getSessionIdFromCookies(cookies);
        Session s = sessionService.get(sessionToken);
        if (s!=null && sessionService.isValid(s.getToken()) ) { 	
        	logger.debug("Session found for username {}",s.getUsername());
            httpExchange.setAttribute(CustomHttpServer.USERNAME_ATTRIBUTE, s.getUsername());       
            httpExchange.setAttribute(CustomHttpServer.SESSION_ATTRIBUTE, s.getToken());
            chain.doFilter(httpExchange);
        } else {
        	logger.info("No valid session found. Redirecting to login page");
            httpExchange.getResponseHeaders().add(LOCATION_HEADER, loginUrl + INC_PARAM + httpExchange.getRequestURI());
            httpExchange.sendResponseHeaders(HttpStatus.SEE_OTHER.value(), -1L);
        }
    }

    private String getSessionIdFromCookies(List<String> cookies) {
        String sessionToken = null;
        if (cookies != null) {
            for (String cookie : cookies) {
                sessionToken = getSessionIdFromCookie(cookie);
                if (sessionToken != null) break;
            }
        }
        return sessionToken;
    }

    private String getSessionIdFromCookie(String cookie) {
        String sessionToken = null;
        if (cookie != null) {
            int index = cookie.indexOf("=");
            if (index > 0) {
                String key = cookie.substring(0, index);
                if (CustomHttpServer.SESSION_KEY.equals(key)) {
                    sessionToken = cookie.substring(index + 1);
                }
            }
        }
        return sessionToken;
    }

    @Override
    public String description() {
        return null;
    }


}