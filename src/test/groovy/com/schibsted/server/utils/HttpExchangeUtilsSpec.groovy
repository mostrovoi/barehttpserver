package com.schibsted.server.utils

import com.sun.net.httpserver.HttpExchange
import spock.lang.Specification
import com.schibsted.server.CustomHttpServerConstants
import com.schibsted.server.utils.HttpExchangeUtils

class HttpExchangeUtilsSpec extends Specification {

	def "Returns logged username"() {
		given:
			HttpExchange he = Mock(HttpExchange.class)
		    he.getAttribute(CustomHttpServerConstants.USERNAME_ATTRIBUTE) >> "perrito"
		when:
			def username = HttpExchangeUtils.getLoggedUsername(he)
		then:
			username == "perrito"
	}

	def "Returns sessionId"() {
		given:
			HttpExchange he = Mock(HttpExchange.class)
			he.getAttribute(CustomHttpServerConstants.SESSION_ATTRIBUTE) >> "12345678"
		when:
			def session = HttpExchangeUtils.getCurrentSessionId(he)
		then:
			session == "12345678"
	}
	
	def "Returns null as sessionId because it does not existd"() {
		given:
			HttpExchange he = Mock(HttpExchange.class)
		when:
			def session = HttpExchangeUtils.getCurrentSessionId(he)
		then:
			session == null
	}
	
}
