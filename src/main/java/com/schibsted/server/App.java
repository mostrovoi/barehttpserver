package com.schibsted.server;

import com.schibsted.server.auth.CustomBasicAuthenticator;
import com.schibsted.server.domain.User.Role;
import com.schibsted.server.service.SessionService;
import com.schibsted.server.service.UserService;
import com.schibsted.server.service.impl.SessionServiceImpl;
import com.schibsted.server.service.impl.UserServiceImpl;
import com.schibsted.server.view.handler.ApiHandler;
import com.schibsted.server.view.handler.LogoutHandler;
import com.schibsted.server.view.handler.PageHandler;
import com.sun.net.httpserver.BasicAuthenticator;

public class App {
	
	//TODO: What to do with exception?
  public static void main(String[] args) throws Exception {
	  
	  final UserService userService = new UserServiceImpl();
      final SessionService sessionService = new SessionServiceImpl();
	  final CustomHttpServer myServer =  new CustomHttpServer();
	  
	  final BasicAuthenticator auth = new CustomBasicAuthenticator(userService, sessionService);
	  
	  myServer.createContext("/page1", new PageHandler(userService, Role.PAGE_1)).setAuthenticator(auth);
	  myServer.createContext("/page2", new PageHandler(userService, Role.PAGE_2)).setAuthenticator(auth);
	  myServer.createContext("/page3", new PageHandler(userService, Role.PAGE_3)).setAuthenticator(auth);
	  myServer.createContext("/logout", new LogoutHandler(sessionService)).setAuthenticator(auth);	  
	  myServer.createContext("/api/users", new ApiHandler(userService)).setAuthenticator(auth);
	  myServer.start();
  }

}