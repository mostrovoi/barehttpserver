package com.schibsted.server.view.handler;

import java.io.IOException;

import com.schibsted.server.utils.HttpExchangeUtil;
import com.schibsted.server.view.dto.PageResponseDTO;
import com.sun.net.httpserver.HttpExchange;

public class IndexPageHandler extends AbstractBaseHandler {

	private static final String INDEX_PAGE_TEMPLATE_NAME = "index.mustache";

	@Override
	public void handle(HttpExchange he) throws IOException {
		String username = HttpExchangeUtil.getLoggedUsername(he);
		String pageHtml = HttpExchangeUtil.createHtml(INDEX_PAGE_TEMPLATE_NAME, new PageResponseDTO("Index",username));
		HttpExchangeUtil.sendHtmlOK(he, pageHtml);
	}

}