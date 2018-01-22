package com.schibsted.server.view.handler

import com.schibsted.server.CustomHttpServerConstants
import com.schibsted.server.domain.User
import com.schibsted.server.domain.User.Role
import com.schibsted.server.service.UserService
import com.schibsted.server.service.impl.UserServiceImpl
import com.schibsted.server.utils.HttpExchangeUtils
import com.schibsted.server.view.dto.APIResponseDTO
import com.sun.security.auth.UserPrincipal

import spock.lang.Specification 

class UserApiHandlerSpec extends Specification {

	private UserApiHandler userApiHandler
	private UserService us
	
	def setup() {
		us = new UserServiceImpl()
		userApiHandler = new UserApiHandler(us,Role.ADMIN);
	}

	def "Petition for GET operation and for user with no admin role should return allowed"() {
		when:
			def result = userApiHandler.isOperationAllowedForUser("user1","GET",Role.ADMIN);
		then:
			result == true
	}
	
	def "Petition for create operation and for user with no admin role should return not allowed"() {
		when:
			def result = userApiHandler.isOperationAllowedForUser("user1","POST",Role.ADMIN);
		then:
			result == false
	}
	
	def "Read operation for null user returns OK"() {
		when:
			APIResponseDTO result = userApiHandler.handleReadOperation(null);
		then:
			result.statusCode == 200
			result.body.length() > 0	
	}
	
	def "Read operation for non existing user returns 404"() {
		when:
			APIResponseDTO result = userApiHandler.handleReadOperation("noexiste");
		then:
			result.statusCode == 404
	}

}
