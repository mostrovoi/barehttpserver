package com.schibsted.server.filter;

import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.server.CustomHttpServer;
import com.schibsted.server.service.SessionService;
import com.schibsted.server.service.UserService;
import com.schibsted.server.utils.HttpExchangeUtil;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

public class AuthenticationFilter extends Filter {

    private final SessionService sessionService;
    private final UserService userService;

	private static final Logger logger = LogManager.getLogger(AuthenticationFilter.class);
	
    public AuthenticationFilter(SessionService ss, UserService us) {
        this.sessionService = ss;
        this.userService = us;
    }

    @Override
    public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {
    	//if valid session has been found already
    	if(httpExchange.getAttribute(CustomHttpServer.USERNAME_ATTRIBUTE) != null) {
    		chain.doFilter(httpExchange);
    	}
    	else {
        	logger.debug("Hello 3");
	    	Map<String,String> params = HttpExchangeUtil.getFormParametersFromBody(httpExchange.getRequestBody());
	    	//TODO: Aqui casca!
	    	String username = null;
	    	String password = null;
        	logger.debug("Hello 4");
	    	if(params != null) {
	    		username = params.get("username");
	    		password = params.get("password");
	    	}
        	logger.debug("Hello 5");
	        if (userService.checkCredentials(username, password)) {
	    	    String sessionid = sessionService.create(username);
	            httpExchange.setAttribute(CustomHttpServer.USERNAME_ATTRIBUTE, username);       
	            httpExchange.setAttribute(CustomHttpServer.SESSION_ATTRIBUTE, sessionid);
				logger.debug("User {} logged in", username);
	            chain.doFilter(httpExchange);
	        } else {
	        	//TODO: Remove cookie / session
	        	chain.doFilter(httpExchange);
	        }
    	}
    }


    @Override
    public String description() {
        return "Authentication filter";
    }


}