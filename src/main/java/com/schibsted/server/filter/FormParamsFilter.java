package com.schibsted.server.filter;

import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.server.CustomHttpServerConstants;
import com.schibsted.server.service.SessionService;
import com.schibsted.server.service.UserService;
import com.schibsted.server.utils.StreamUtils;
import com.schibsted.server.utils.UrlParserUtils;
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
    	
		httpExchange.setAttribute(CustomHttpServerConstants.LOGIN_ERROR_ATTRIBUTE, "0");   

		if(httpExchange.getAttribute(CustomHttpServerConstants.USERNAME_ATTRIBUTE) == null) {
			//TODO: Dont take content-type for granted: It should be Content-Type: application/x-www-form-urlencoded;
			//https://www.w3.org/TR/html5/sec-forms.html#application-x-www-form-urlencoded-encoding-algorithm
			String requestBody = StreamUtils.convertInputStreamToString(httpExchange.getRequestBody(), StreamUtils.UTF_8);
	    	Map<String,String> params = UrlParserUtils.getFormParametersFromBody(requestBody);
	    	if(params != null && params.size() > 0) {
        		String username = params.get(CustomHttpServerConstants.FORM_USERNAME);
	    		String password = params.get(CustomHttpServerConstants.FORM_PASS);
	    		String url = params.get(CustomHttpServerConstants.FORM_URL);
  
	        	if (username != null && userService.checkCredentials(username, password)) {
		    	    String sessionid = sessionService.create(username);
		            httpExchange.setAttribute(CustomHttpServerConstants.USERNAME_ATTRIBUTE, username);       
		            httpExchange.setAttribute(CustomHttpServerConstants.SESSION_ATTRIBUTE, sessionid);
		            httpExchange.setAttribute(CustomHttpServerConstants.REDIRECT_ATTRIBUTE, url); 
		            httpExchange.setAttribute(CustomHttpServerConstants.LOGIN_ERROR_ATTRIBUTE, "0"); 
					logger.debug("User {} logged in", username);
		        }
	        	else
	        	    httpExchange.setAttribute(CustomHttpServerConstants.LOGIN_ERROR_ATTRIBUTE, "1"); 
					
	        }
	    }
        chain.doFilter(httpExchange);	
    }


    @Override
    public String description() {
        return "Extracts username and password from form parameters and validates those. If successful, sets username as attribute";
    }


}