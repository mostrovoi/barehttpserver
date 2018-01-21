package com.schibsted.server.view.handler

import spock.lang.Specification

import com.schibsted.server.service.SessionService

import com.schibsted.server.view.handler.UserApiHandler
class UserApiHandlerSpec extends Specification {

	private UserApiHandler userApiHandler
	def setup() {
		userApiHandler = new UserApiHandler();
	}

}
