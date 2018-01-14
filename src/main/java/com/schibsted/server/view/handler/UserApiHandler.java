package com.schibsted.server.view.handler;

import java.io.IOException;
import java.io.InputStream;

import com.danisola.restify.url.RestParser;
import com.danisola.restify.url.RestUrl;
import com.schibsted.server.domain.User;
import com.schibsted.server.domain.User.Role;
import com.schibsted.server.service.UserService;
import com.schibsted.server.utils.ApiUtils;
import com.schibsted.server.utils.HttpStatus;
import com.schibsted.server.utils.RequestMethod;
import com.schibsted.server.view.rest.APIResponseDTO;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import static com.danisola.restify.url.RestParserFactory.parser;
import static com.danisola.restify.url.types.StrVar.strVar;

public class UserApiHandler implements HttpHandler {

	private final UserService userService;
	private final Role validRole;

	private final static String USERS_PATH = "/api/users/";
	
	public UserApiHandler(UserService us, Role r) {
		this.userService = us;
		this.validRole = r;
	}
	
	@Override
	public void handle(HttpExchange he) throws IOException {
		String path = he.getRequestURI().getPath();
	
		String username = he.getPrincipal().getUsername();
		String method = he.getRequestMethod();
		String body  = ApiUtils.convertStreamToString(he.getRequestBody(), "UTF-8");
		//First off, handle authorization
		if(!isOperationAllowedForUser(username,method,validRole))
			he.sendResponseHeaders(HttpStatus.UNAUTHORIZED.value(), -1L);
			
		//Parse URL
		String requestedUsername = ApiUtils.getUsernameFromPath(path, USERS_PATH);
		if(requestedUsername != null) {
		    if( userService.getUserByUsername(requestedUsername) == null)
		    	he.sendResponseHeaders(HttpStatus.NOT_FOUND.value(), -1L);
		}
		
		switch(method) {
			case RequestMethod.GET.value():
				handleReadOperation(requestedUsername);
				break;
			case RequestMethod.POST.value():
				handleCreateOperation(requestedUsername);
				break;
			//PUT is idempotent
			case RequestMethod.PUT.value():
				handleUpdateOperation(requestedUsername,he.getRequestBody());
				break;

			case RequestMethod.DELETE.value():
				handleDeleteOperation(path);
				break;
			//not supported operations
			case RequestMethod.OPTIONS.value():
			case RequestMethod.PATCH.value():
			case RequestMethod.HEAD.value():
			default:
				he.sendResponseHeaders(HttpStatus.METHOD_NOT_ALLOWED.value(),-1L);
				break;
		}

	}
	
	private  boolean isOperationAllowedForUser(String username, String method, Role validRole) {
		if( ! RequestMethod.GET.value().equals(method) ) 
			 return userService.hasUserRole(username, validRole);
		return true;
	}
	
	//Body for GET operation has no defined semantics (HTTP 1.1 RFC 7231) -> ignore it
	private APIResponseDTO handleReadOperation(String requestedUsername) {
		
	}
	
	private APIResponseDTO handleCreateOperation(String path, InputStream body) {
		
	}
	
	private APIResponseDTO handleUpdateOperation(String requestedUsername) {
		
	}
	
	//Body for DELETE operation has no defined semantics (HTTP 1.1 RFC 7231) -> ignore it
	private APIResponseDTO handleDeleteOperation(String requestedUsername) {
		//collection removal is not allowed
		if(requestUsername==null) 
			return new APIResponseDTO(HttpStatus.BAD_REQUEST.value());
		else
			userService.
	}

}

