package com.schibsted.server.view.handler;

import java.io.IOException;

import com.schibsted.server.view.dto.PageResponseDTO;
import com.sun.net.httpserver.HttpExchange;

public class FormLoginHandler extends AbstractBaseHandler {

	private static final String FORM_LOGIN_TEMPLATE_NAME = "login.mustache";

	@Override
	public void handle(HttpExchange he) throws IOException {
		String loginForm = createHtml(FORM_LOGIN_TEMPLATE_NAME, new PageResponseDTO("Login"));
		sendOK(he, loginForm);
	}
}