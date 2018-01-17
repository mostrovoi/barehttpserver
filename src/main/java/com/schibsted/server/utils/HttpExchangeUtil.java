package com.schibsted.server.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.schibsted.server.CustomHttpServer;
import com.sun.net.httpserver.HttpExchange;

public class HttpExchangeUtil
{
	
   private HttpExchangeUtil() {
   }
   

   public static Map<String,String> getFormParametersFromBody(InputStream is){
	  //FIXME: Take it for granted:  Content-Type: application/x-www-form-urlencoded;
	  //SEE: https://www.w3.org/TR/html5/sec-forms.html#application-x-www-form-urlencoded-encoding-algorithm
	   Map<String,String> params = new HashMap<String,String>();
	   String input = StreamUtils.convertInputStreamToString(is,StreamUtils.UTF_8);
	   
	   Pattern p = Pattern.compile("(?:(\\w*)=(\\w*)(?=&|$))");
	   Matcher m = p.matcher(input);   
	   
	   while (m.find()) {
		  params.put(m.group(1),m.group(2));
	   }
	   return (params.isEmpty()) ? null : params;
   }
   

   public static String getSessionIdFromCookies(HttpExchange httpExchange) {
	   
	   //	  //TODO: Get cookies values from it
		//  HttpCookie.parse("dd");
	   //
       List<String> cookies = httpExchange.getRequestHeaders().get("Cookie");
       String sessionToken = null;
       if (cookies != null) {
           for (String cookie : cookies) {
               sessionToken = getSessionIdFromCookie(cookie);
               if (sessionToken != null) break;
           }
       }
       return sessionToken;
   }	

   private static String getSessionIdFromCookie(String cookie) {
       String sessionToken = null;
       if (cookie != null) {
           int index = cookie.indexOf("=");
           if (index > 0) {
               String key = cookie.substring(0, index);
               if (CustomHttpServer.SESSION_KEY.equals(key)) {
                   sessionToken = cookie.substring(index + 1);
               }
           }
       }
       return sessionToken;
   }

}
