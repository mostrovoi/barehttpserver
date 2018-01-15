package com.schibsted.server.dao;

import spock.lang.Specification;
import com.schibsted.server.domain.User;
import static com.schibsted.server.domain.User.Role;

class UserDaoSpec extends Specification {


	def "A new user with null roles should throw a NullPointerException"() {
		when:
			User u = new User("aaaa","bbb",null);
		then:
			thrown NullPointerException;
	}
	
}
