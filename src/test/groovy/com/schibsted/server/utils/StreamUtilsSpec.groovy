package com.schibsted.server.utils;

import spock.lang.Specification;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import com.schibsted.server.utils.StreamUtils;

class StreamUtilsSpec extends Specification {

	def "empty Inputstream should return empty string"() {
		given:
			String originalText = "";
			InputStream ts = StreamUtilsSpec.getInputStream(originalText);
		when:
			String text2 = StreamUtils.convertInputStreamToString(ts, StreamUtils.ISO_8859_1);
		then:
			text2.equals(originalText);
	}
 	
	def "Inputstream should return original string"() {
		given:
			String originalText = "1234";
			InputStream ts = StreamUtilsSpec.getInputStream(originalText);
		when:
			String text2 = StreamUtils.convertInputStreamToString(ts, StreamUtils.UTF_8);
		then:
			originalText.equals(text2);
	}
	
	static InputStream getInputStream(String text){
		if(text==null)
			return null;
		else
	       return new ByteArrayInputStream(text.getBytes());               
	}                     
}
