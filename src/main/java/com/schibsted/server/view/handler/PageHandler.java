package com.schibsted.server.view.handler;

import java.io.IOException;

import org.apache.http.HttpStatus;

import com.schibsted.server.domain.User.Role;
import com.schibsted.server.service.UserService;
import com.schibsted.server.utils.HttpServerUtils;
import com.schibsted.server.utils.PageTemplate;
import com.schibsted.server.utils.ViewBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class PageHandler implements HttpHandler {

	private static final String PAGE_TEMPLATE_NAME = "page.mustache";
	private static final String ERROR_TEMPLATE_NAME = "error.mustache";
	
	private final Role validRole;
	private final UserService userService;
	
	public PageHandler(UserService us, Role r) {
		this.userService = us;
		this.validRole = r;
	}

	@Override
	public void handle(HttpExchange he) throws IOException {

		String username = he.getPrincipal().getUsername();
		
		if(this.userService.hasUserRole(username, validRole)) {
			//TODO: Page name based on role. Util class
	        String pageHtml = ViewBuilder.create(PAGE_TEMPLATE_NAME, new PageTemplate("Page ",username));
	        HttpServerUtils.send(he, pageHtml,HttpStatus.SC_OK);
		}
		else {
			//TODO: Util class to get description based on error code
	        String errorHtml = ViewBuilder.create(ERROR_TEMPLATE_NAME, new PageTemplate("Error",username,"SS"));
	        HttpServerUtils.send(he, errorHtml,HttpStatus.SC_FORBIDDEN);
	        
		}
	}

}