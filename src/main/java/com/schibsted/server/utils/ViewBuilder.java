package com.schibsted.server.utils;

import java.io.IOException;
import java.io.StringWriter;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

public class ViewBuilder {
	
	public static String create(String templateName, PageTemplate pageTemplate) throws IOException {
		
		//TODO: See implications of making just one instance of factory
		MustacheFactory mf = new DefaultMustacheFactory();
		Mustache mustache = mf.compile(templateName);
		StringWriter writer = new StringWriter();
		mustache.execute(writer, pageTemplate).flush();
		return writer.toString();
	}
}
