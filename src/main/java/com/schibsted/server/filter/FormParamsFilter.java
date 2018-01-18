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

public class FormParamsFilter extends Filter {

    private final SessionService sessionService;
    private final UserService userService;

	private static final Logger logger = LogManager.getLogger(FormParamsFilter.class);
	
    public FormParamsFilter(SessionService ss, UserService us) {
        this.sessionService = ss;
        this.userService = us;
    }

    @Override
    public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {
		httpExchange.setAttribute(CustomHttpServer.LOGIN_ERROR_ATTRIBUTE, "0");   
    	//if no valid session has been found already
		if(httpExchange.getAttribute(CustomHttpServer.USERNAME_ATTRIBUTE) == null) {
	    	Map<String,String> params = HttpExchangeUtil.getFormParametersFromBody(httpExchange.getRequestBody());
	    	if(params != null) {
        		String username = params.get("username");
	    		String password = params.get("password");
   
	        	if (username != null && userService.checkCredentials(username, password)) {
		    	    String sessionid = sessionService.create(username);
		            httpExchange.setAttribute(CustomHttpServer.USERNAME_ATTRIBUTE, username);       
		            httpExchange.setAttribute(CustomHttpServer.SESSION_ATTRIBUTE, sessionid);
		            httpExchange.setAttribute(CustomHttpServer.LOGIN_ERROR_ATTRIBUTE, "0"); 
					logger.debug("User {} logged in", username);
		        }
	        	else
	        	    httpExchange.setAttribute(CustomHttpServer.LOGIN_ERROR_ATTRIBUTE, "1"); 
					
	        }
	    }
        chain.doFilter(httpExchange);	
    }


    @Override
    public String description() {
        return "Extracts username and password from form parameters and validates those. If successful, sets username as attribute";
    }


}