package com.schibsted.server.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class StreamUtils {

	private StreamUtils() {
	}

	public static final String UTF_8 = "UTF-8";
	public static final String ISO_8859_1 = "ISO-8859-1";

	public static String convertInputStreamToString(InputStream is, String charset) {
		try {
			if (is.available() == 0)
				return "";
		} catch (IOException e) {
			return null;
		}
		Scanner scanner = new Scanner(is, charset);
		return scanner.useDelimiter("\\A").next();
	}
}
