package com.schibsted.server.view.handler;

import java.io.IOException;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.schibsted.server.CustomHttpServerConstants;
import com.schibsted.server.domain.User;
import com.schibsted.server.domain.User.Role;
import com.schibsted.server.exception.UserExistsException;
import com.schibsted.server.exception.UsernameNotFoundException;
import com.schibsted.server.service.UserService;
import com.schibsted.server.utils.HeadersUtils;
import com.schibsted.server.utils.HttpExchangeUtils;
import com.schibsted.server.utils.HttpStatus;
import com.schibsted.server.utils.RequestMethod;
import com.schibsted.server.utils.StreamUtils;
import com.schibsted.server.view.dto.APIResponseDTO;
import com.sun.net.httpserver.HttpExchange;

public class UserApiHandler extends AbstractBaseHandler {

	private final UserService userService;
	private final Role validRole;
	private static final String USER = CustomHttpServerConstants.PATHLEVEL_ATTRIBUTE + 3;
	private static final String EXTRA_PARAM = CustomHttpServerConstants.PATHLEVEL_ATTRIBUTE + 4;

	private Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

	public UserApiHandler(UserService us, Role r) {
		this.userService = us;
		this.validRole = r;
	}

	@Override
	public void handle(HttpExchange he) throws IOException {

		String username = he.getPrincipal().getUsername();
		String method = he.getRequestMethod();
		APIResponseDTO responseDtO = null;
		// First off, handle authorization
		if (!isOperationAllowedForUser(username, method, validRole)) {
			he.sendResponseHeaders(HttpStatus.FORBIDDEN.value(), -1L);
			he.close();
		}

		// Get requested username and request body
		String requestBody = StreamUtils.convertInputStreamToString(he.getRequestBody(), StreamUtils.UTF_8);
		Map<String, String> parameters = (Map<String,String>) he
				.getAttribute(CustomHttpServerConstants.PARAMETERS_ATTRIBUTE);
		String requestedUsername = null;
		String extraParam = null;
		if (parameters != null) {
			requestedUsername = parameters.get(USER);
			extraParam = parameters.get(EXTRA_PARAM);
		}

		if (extraParam != null) {
			responseDtO = new APIResponseDTO(HttpStatus.NOT_FOUND.value(), "");
		}

		else {
			switch (RequestMethod.valueOf(method)) {
			case GET:
				responseDtO = handleReadOperation(requestedUsername);
				break;
			case POST:
				responseDtO = handleCreateOperation(requestedUsername, requestBody);
				break;
			case PUT:
				responseDtO = handleUpdateOperation(requestedUsername, requestBody);
				break;
			case DELETE:
				responseDtO = handleDeleteOperation(requestedUsername);
				break;
			//TODO: To add support in the future
			case OPTIONS:
			case PATCH:
			case HEAD:
			default:
				responseDtO = new APIResponseDTO(HttpStatus.METHOD_NOT_ALLOWED.value(), "");
				break;
			}
		}
		HttpExchangeUtils.send(he, responseDtO.getBody(), HeadersUtils.CONTENT_TYPE_JSON, responseDtO.getStatusCode());
	}

	private boolean isOperationAllowedForUser(String username, String method, Role validRole) {
		if (!RequestMethod.GET.value().equals(method))
			return userService.hasUserRole(username, validRole);
		return true;
	}

	// Body for GET operation has no defined semantics (HTTP 1.1 RFC 7231) ->
	// ignore it
	private APIResponseDTO handleReadOperation(String requestedUsername) {
		APIResponseDTO dto = null;
		if (requestedUsername == null) {
			String json = gson.toJson(userService.getUsers());
			dto = new APIResponseDTO(HttpStatus.OK.value(), json);
		} else {
			User u = userService.getUserByUsername(requestedUsername);
			if (u == null)
				dto = new APIResponseDTO(HttpStatus.NOT_FOUND.value(), "");
			else
				dto = new APIResponseDTO(HttpStatus.OK.value(), gson.toJson(u));
		}
		return dto;
	}

	/**
	 * Adds a new user. No username can be specified at the URL level. All
	 * information is sent via body
	 * 
	 * @param requestedUsername
	 *            must be null
	 * @param body
	 *            contains the new user to be inserted
	 * @return APIResponseDTO containing the response body and status code
	 */
	private APIResponseDTO handleCreateOperation(String requestedUsername, String body) {
		if (requestedUsername != null)
			return new APIResponseDTO(HttpStatus.BAD_REQUEST.value(), "");

		try {
			User u = gson.fromJson(body, User.class);
			userService.create(u);
		} catch (UserExistsException e) {
			logger.warn("The username {} already exists. Not able to create it", requestedUsername);
			return new APIResponseDTO(HttpStatus.BAD_REQUEST.value(), "");
		} catch (Exception e) {
			String error = "Unable to create user from given request body";
			logger.warn(error);
			return new APIResponseDTO(HttpStatus.BAD_REQUEST.value(), "");
		}
		return new APIResponseDTO(HttpStatus.OK.value(), "");
	}

	private APIResponseDTO handleUpdateOperation(String requestedUsername, String body) {

		if (requestedUsername == null)
			return new APIResponseDTO(HttpStatus.BAD_REQUEST.value(), "");

		User u = userService.getUserByUsername(requestedUsername);
		if (u == null)
			return new APIResponseDTO(HttpStatus.BAD_REQUEST.value(), "");
		else {
			try {
				User newUser = gson.fromJson(body, User.class);

				if (!requestedUsername.equals(newUser.getUsername())) {
					logger.warn("Username in payload is different that the one set in URL");
					return new APIResponseDTO(HttpStatus.BAD_REQUEST.value(), "");
				}
				userService.update(newUser);
			} catch (UsernameNotFoundException e) {
				logger.warn("Unable to update user. Username not found {}", requestedUsername);
				return new APIResponseDTO(HttpStatus.NOT_FOUND.value(), "");
			} catch (Exception e) {
				logger.warn("Unable to update user from given request body");
				return new APIResponseDTO(HttpStatus.BAD_REQUEST.value(), "");
			}
			return new APIResponseDTO(HttpStatus.OK.value(), "");
		}

	}

	// Body for DELETE operation has no defined semantics (HTTP 1.1 RFC 7231) ->
	// ignore it
	private APIResponseDTO handleDeleteOperation(String requestedUsername) {
		// collection removal is not allowed
		if (requestedUsername == null)
			return new APIResponseDTO(HttpStatus.BAD_REQUEST.value());
		else {
			if (!userService.delete(requestedUsername))
				return new APIResponseDTO(HttpStatus.NOT_FOUND.value(), "");
			return new APIResponseDTO(HttpStatus.OK.value(), "");
		}
	}

}
