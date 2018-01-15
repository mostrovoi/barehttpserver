package com.schibsted.server.view.handler;

import java.io.IOException;
import java.io.InputStream;

import com.schibsted.server.domain.User.Role;
import com.schibsted.server.service.UserService;
import com.schibsted.server.service.UsernameNotFoundException;
import com.schibsted.server.utils.HttpStatus;
import com.schibsted.server.utils.RequestMethod;
import com.schibsted.server.utils.StreamUtils;
import com.schibsted.server.view.dto.APIResponseDTO;
import com.sun.net.httpserver.HttpExchange;

public class UserApiHandler extends AbstractBaseHandler {

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
		String body  = StreamUtils.convertInputStreamToString(he.getRequestBody(), StreamUtils.UTF_8);
		//First off, handle authorization
		if(!isOperationAllowedForUser(username,method,validRole))
			he.sendResponseHeaders(HttpStatus.UNAUTHORIZED.value(), -1L);
			
		//Parse URL
		String requestedUsername = getUsernameFromPath(path, USERS_PATH);
		if(requestedUsername != null) {
			if( userService.getUserByUsername(requestedUsername) == null)
				he.sendResponseHeaders(HttpStatus.NOT_FOUND.value(), -1L);
		}
		
		switch(RequestMethod.valueOf(method)) {
			case GET:
				handleReadOperation(requestedUsername);
				break;
			case POST:
				handleCreateOperation(requestedUsername,he.getRequestBody());
				break;
			//PUT is idempotent
			case PUT:
				handleUpdateOperation(requestedUsername,he.getRequestBody());
				break;

			case DELETE:
				handleDeleteOperation(path);
				break;
			//not supported operations
			case OPTIONS:
			case PATCH:
			case HEAD:
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
		return null;
	}
	
	private APIResponseDTO handleCreateOperation(String path, InputStream body) {
		return null;
	}
	
	private APIResponseDTO handleUpdateOperation(String requestedUsername,  InputStream body) {
		return null;
	}
	
	//Body for DELETE operation has no defined semantics (HTTP 1.1 RFC 7231) -> ignore it
	private APIResponseDTO handleDeleteOperation(String requestedUsername) {
		//collection removal is not allowed
		if(requestedUsername==null) 
			return new APIResponseDTO(HttpStatus.BAD_REQUEST.value());
		else {
			try {
				userService.delete(requestedUsername);
			} catch (UsernameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}

}

