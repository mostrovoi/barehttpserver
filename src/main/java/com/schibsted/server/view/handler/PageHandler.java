package com.schibsted.server.view.handler;

import java.io.IOException;

import com.schibsted.server.domain.User.Role;
import com.schibsted.server.service.UserService;
import com.schibsted.server.utils.HeadersUtil;
import com.schibsted.server.utils.HttpExchangeUtil;
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

		String username = HttpExchangeUtil.getLoggedUsername(he);
		if(this.userService.hasUserRole(username, validRole)) {
	        String pageHtml = HttpExchangeUtil.createHtml(PAGE_TEMPLATE_NAME, new PageResponseDTO("Page ",username));
	        HttpExchangeUtil.sendHtmlOK(he, pageHtml);
		}
		else {
	        String errorHtml = HttpExchangeUtil.createHtml(ERROR_TEMPLATE_NAME, new PageResponseDTO("Error",username,"Not allowed"));
	        HttpExchangeUtil.send(he, errorHtml,HeadersUtil.CONTENT_TYPE_HTML,HttpStatus.FORBIDDEN.value());
	        
		}
	}

}