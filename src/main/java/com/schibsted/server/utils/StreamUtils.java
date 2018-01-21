package com.schibsted.server.utils;

import java.io.InputStream;
import java.util.Scanner;

public class StreamUtils {

	private StreamUtils() {
	}

	public static final String UTF_8 = "UTF-8";
	public static final String ISO_8859_1 = "ISO-8859-1";

	public static String convertInputStreamToString(InputStream is, String charset) {
		Scanner scanner = new Scanner(is, charset).useDelimiter("\\A");
		String result = "";
		if (scanner.hasNext()) 
			result = scanner.next();
		//closes inputstream too
		scanner.close();
		return result;
	}
}
