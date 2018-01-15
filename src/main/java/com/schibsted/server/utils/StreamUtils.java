package com.schibsted.server.utils;

import java.io.InputStream;
import java.util.Scanner;

public class StreamUtils {

	private StreamUtils() {
	}
	
	public final static String UTF_8 = "UTF-8";
	
	public static String convertInputStreamToString(InputStream is, String charset) {
		Scanner scanner = new Scanner(is, charset);
		return scanner.useDelimiter("\\A").next();
	}
}
