package com.schibsted.server;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.server.auth.CustomAuthenticator;
import com.schibsted.server.domain.User.Role;
import com.schibsted.server.filter.FormParamsFilter;
import com.schibsted.server.filter.RedirectFilter;
import com.schibsted.server.filter.SessionFilter;
import com.schibsted.server.service.SessionService;
import com.schibsted.server.service.UserService;
import com.schibsted.server.service.impl.SessionGuavaServiceImpl;
import com.schibsted.server.service.impl.UserServiceImpl;
import com.schibsted.server.view.handler.IndexPageHandler;
import com.schibsted.server.view.handler.LoginHandler;
import com.schibsted.server.view.handler.LogoutHandler;
import com.schibsted.server.view.handler.PageHandler;
import com.schibsted.server.view.handler.UserApiHandler;
import com.sun.net.httpserver.BasicAuthenticator;

public class App {

	private static final Logger logger = LogManager.getLogger(App.class);

	public static void main(String[] args) throws IOException {

		final UserService userService = new UserServiceImpl();
		final SessionService sessionService = new SessionGuavaServiceImpl();
		final CustomHttpServer myServer = new CustomHttpServer();

		final String loginUrl = myServer.getUrl() + "/login";

		final BasicAuthenticator auth = new CustomAuthenticator(userService);

		final SessionFilter sessionFilter = new SessionFilter(sessionService);
		final RedirectFilter redirectFilter = new RedirectFilter(loginUrl);
		final FormParamsFilter paramsFilter = new FormParamsFilter(sessionService, userService);

		myServer.createContextWithFilters("/login", new LoginHandler(), sessionFilter, paramsFilter);
		myServer.createContextWithFilters("/private", new IndexPageHandler(), sessionFilter, redirectFilter);
		myServer.createContextWithFilters("/private/page1", new PageHandler(userService, Role.PAGE_1), sessionFilter,
				redirectFilter);
		myServer.createContextWithFilters("/private/page2", new PageHandler(userService, Role.PAGE_2), sessionFilter,
				redirectFilter);
		myServer.createContextWithFilters("/private/page3", new PageHandler(userService, Role.PAGE_3), sessionFilter,
				redirectFilter);
		myServer.createContextWithFilters("/private/logout", new LogoutHandler(sessionService), sessionFilter,
				redirectFilter);
		myServer.createContext("/api/users", new UserApiHandler(userService, Role.ADMIN)).setAuthenticator(auth);
		myServer.start();
		logger.info("Server started in {}", myServer.getHost());
		logger.info("Start by logging in at {}", loginUrl);
	}
}