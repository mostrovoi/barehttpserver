package com.schibsted.server.filter;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.server.CustomHttpServer;
import com.schibsted.server.domain.Session;
import com.schibsted.server.service.SessionService;
import com.schibsted.server.utils.HttpExchangeUtil;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

public class SessionFilter extends Filter {

    
    private SessionService sessionService;
	private static final Logger logger = LogManager.getLogger(SessionFilter.class);
	
    public SessionFilter(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {
        String sessionToken = HttpExchangeUtil.getSessionIdFromCookies(httpExchange);
        Session s = sessionService.get(sessionToken);
        if (s!=null && sessionService.isValid(s.getToken()) ) { 	
        	logger.debug("Session found for username {}",s.getUsername());
            httpExchange.setAttribute(CustomHttpServer.USERNAME_ATTRIBUTE, s.getUsername());       
            httpExchange.setAttribute(CustomHttpServer.SESSION_ATTRIBUTE, s.getToken());
            chain.doFilter(httpExchange);
        } else {
            chain.doFilter(httpExchange);
        }
    }

    @Override
    public String description() {
        return "Session filter that checks for valid session and extracts username into attribute if found";
    }


}