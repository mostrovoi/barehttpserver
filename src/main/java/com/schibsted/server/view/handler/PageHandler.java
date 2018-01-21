package com.schibsted.server.view.handler;

import java.io.IOException;

import com.schibsted.server.domain.User.Role;
import com.schibsted.server.service.UserService;
import com.schibsted.server.utils.HeadersUtila;
import com.schibsted.server.utils.HttpExchangeUtils;
import com.schibsted.server.utils.HttpStatus;
import com.schibsted.server.view.dto.PageResponseDTO;
import com.sun.net.httpserver.HttpExchange;

public class PageHandler extends AbstractBaseHandler {

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

		String username = HttpExchangeUtils.getLoggedUsername(he);
		if(this.userService.hasUserRole(username, validRole)) {
	        String pageHtml = HttpExchangeUtils.createHtml(PAGE_TEMPLATE_NAME, new PageResponseDTO("Page ",username));
	        HttpExchangeUtils.sendHtmlOK(he, pageHtml);
		}
		else {
	        String errorHtml = HttpExchangeUtils.createHtml(ERROR_TEMPLATE_NAME, new PageResponseDTO("Error",username,"Not allowed"));
	        HttpExchangeUtils.send(he, errorHtml,HeadersUtila.CONTENT_TYPE_HTML,HttpStatus.FORBIDDEN.value());
	        
		}
	}

}