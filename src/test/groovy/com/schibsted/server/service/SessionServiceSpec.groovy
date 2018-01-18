package com.schibsted.server.service

import com.schibsted.server.service.impl.SessionGuavaServiceImpl

import spock.lang.Specification

class SessionServiceSpec extends Specification {
	
	private SessionService sessionService
	
		def setup() {
			sessionService = new SessionGuavaServiceImpl()
		}
		
		
		def "should return a new session id when creating a session"() {
			when:
				def sessionId = sessionService.create("aaa")
			then: 
				sessionId != null
		}	
		
		def "session service should store user info when creating new session"() {
			when:
				def sessionId = sessionService.create("aab")
			then:
				sessionService.getUsername(sessionId) == "aab"
		}
		
		def "session should be invalidated when deletng session"() {
			given: 
				def sessionId = sessionService.create("aaa")
			when:
				sessionService.delete(sessionId)
			then:
				sessionService.isValid(sessionId) == false
		}
		
		def "session should be new when creating session with same username"() {
			given:
				def session1 = sessionService.create("aaa")
			when:
				def session2 = sessionService.create("aaa")
			then:
				sessionService.getUsername(session2) == "aaa"
				sessionService.getUsername(session1) == "aaa"
		}
}
