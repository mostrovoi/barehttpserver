package com.schibsted.server.view.handler;

import java.io.IOException;

import com.schibsted.server.domain.User.Role;
import com.schibsted.server.service.UserService;
import com.schibsted.server.utils.HttpStatus;
import com.schibsted.server.view.dto.PageResponseDTO;
import com.sun.net.httpserver.HttpExchange;

public class PageHandler extends AbstractBaseHandler {

	private final static String PAGE_TEMPLATE_NAME = "page.mustache";
	private final static String ERROR_TEMPLATE_NAME = "error.mustache";
	
	private final Role validRole;
	private final UserService userService;
	
	public PageHandler(UserService us, Role r) {
		this.userService = us;
		this.validRole = r;
	}

	@Override
	public void handle(HttpExchange he) throws IOException {

		String username = getLoggedUsername(he);
		if(this.userService.hasUserRole(username, validRole)) {
	        String pageHtml = createHtml(PAGE_TEMPLATE_NAME, new PageResponseDTO("Page ",username));
	        send(he, pageHtml,HttpStatus.OK.value());
		}
		else {
	        String errorHtml = createHtml(ERROR_TEMPLATE_NAME, new PageResponseDTO("Error",username,"Not allowed"));
	        send(he, errorHtml,HttpStatus.FORBIDDEN.value());
	        
		}
	}

}