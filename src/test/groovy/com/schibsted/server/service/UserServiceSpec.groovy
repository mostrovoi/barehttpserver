package com.schibsted.server.service;

import spock.lang.Specification;
import com.schibsted.server.domain.User;
import com.schibsted.server.service.impl.UserServiceImpl

import static com.schibsted.server.domain.User.Role;

import com.schibsted.server.dao.UserDao

class UserServiceSpec extends Specification {
	
	private UserService userService
	
	def setup() {
		userService = new UserServiceImpl()
	}
	
	def "A new user should get inserted in DB"() {
		given:
			User u = new User("ramon","bbb",null)
		when:
			userService.create(u)
		then:
			"ramon".equals(userService.getUserByUsername("ramon").getUsername())
	}
	
	def "The method checks credentials should return false when username is not in DB"() {
		when: 
			def authenticate =userService.checkCredentials("noexiste", "daigual")	
		then:
			authenticate == false
	}
	
	def "authenticate returns true when username and password match"() {
		when:
			def authenticate = userService.checkCredentials("user1", "user1")
		then:
			authenticate == true
	}
	
	def "should return true if given user has right role"() {
		when:
		   def hasRole = userService.hasUserRole("user1", Role.PAGE_1)
		then:
			hasRole == true
	}
	
	def "should return false if tries to delete non existing user"() {
		when:
			def deleted = userService.delete("noexiste")
		then:
			deleted == false
	}
	
	def "should return true when deleting existing user"() {
		when:
			def deleted = userService.delete("user1")
		then:
			deleted == true
	}
}