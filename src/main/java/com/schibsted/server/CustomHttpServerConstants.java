package com.schibsted.server;

public class CustomHttpServerConstants {

	private CustomHttpServerConstants() {
	}

	public static final int PORT = 8080;
	public static final String HOST = "localhost:";
	public static final String URL = "http://" + HOST;
	public static final String SESSION_KEY = "JSESSIONID";
	public static final String USERNAME_ATTRIBUTE = "username";
	public static final String SESSION_ATTRIBUTE = "sessionid";
	public static final String LOGIN_ERROR_ATTRIBUTE = "loginerror";
	public static final String FORM_USERNAME = "username";
	public static final String FORM_PASS = "password";
	public static final String PARAMETERS_ATTRIBUTE = "parameters";
	public static final String PATHLEVEL_ATTRIBUTE = "PATHLEVEL";
	public static final String LOCATION_PARAMETER = "location";
	public static final String REDIRECT_ATTRIBUTE = "redirect";
	public static final String FORM_URL = "url";

}
