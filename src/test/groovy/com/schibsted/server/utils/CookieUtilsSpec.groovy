package com.schibsted.server.utils

import spock.lang.Specification
import com.schibsted.server.utils.CookieUtils

class CookieUtilsSpec extends Specification {
	
	def "wrong formed cookie should return valid value"() {
		given:
			def cookies = ["input1=val1","adfadfa","input2=val2","session=333"];
		when:
			String value = CookieUtils.getValue("session",cookies)
		then:
			value == "333"
	}
	
	
	def "non existing key should return null"() {
		given:
			def cookies = ["input1=val1","input2=vale","input2=val2","session=333"];
		when:
			String value = CookieUtils.getValue("perroflauta",cookies)
		then:
			value == null
	}
	
	def "repeated entry should return the first ocurrence"() {
		given:
			def cookies = ["pio=val1","pio=vale","input2=val2","session=333"];
		when:
			String value = CookieUtils.getValue("pio",cookies)
		then:
			value == "val1"
	}
	
	def "given a session key returns the string for sending with set-cookie header for deleting session"() {
		given:
			def sessionId = "JSESSIONID";
		when:
			def sessionDeletedStr = CookieUtilsSpec.getSetDeletedSession(sessionId)
		then:
			sessionDeletedStr == "JSESSIONID==deleted; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT";
	}

}
