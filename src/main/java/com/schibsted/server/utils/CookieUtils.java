package com.schibsted.server.utils;

import java.util.List;

public class CookieUtils {

	private CookieUtils() {}
	
	public static String getValue(String value, List<String> cookies) {
		if(cookies != null && value != null) {
			for(String cookie : cookies) {
				String[] parts = cookie.split("=");
				if (value.equals(parts[0])) 
					return parts[1];
			}
		}
		return null;
	}
	
	public static String getSetDeletedSession(String session) {
		if(session == null)
			return null;
		else
			return session+"=deleted; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT";
	}
}
