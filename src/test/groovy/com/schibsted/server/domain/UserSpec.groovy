package com.schibsted.server.domain;

import spock.lang.Specification; 

import com.schibsted.server.domain.User;
import com.schibsted.server.exception.ValidationException;
import static com.schibsted.server.domain.User.Role;

class UserSpec extends Specification {

	def "A new user with no roles parameter should contain no roles"() {
		when:
			User u = new User("aaaa","bbb")
		then:
			u.getRoles().isEmpty()
	}
	
	def "A new user with invalid username throws exception"() {
		when: 
			User u = new User(username, password)
		then:
			thrown(expectedException)
		where:
		username  |  password || expectedException
		  "AAA"   |   "bbb"   || ValidationException
		  "%fdas" |   "bbb"   || ValidationException
		  null    |   "bbb"   || ValidationException
		  "aaa"   |   null    || ValidationException
 	}
	
	def "A  user with roles set should return the new roles"() {
		when: 
			User u = new User("aaaa","bbb",Role.PAGE_1, Role.PAGE_2);
		then: 
			u.getRoles().size() == 2;
	}
	
	def "A  user with roles repeated should return all roles"() {
		when: 
			User u = new User("aaaa","bbb",Role.PAGE_1,Role.PAGE_2, Role.PAGE_3,Role.PAGE_1,Role.PAGE_2,Role.ADMIN);
		then: 
			u.getRoles().size() == 6;
	}
	
	def "Check that login works: happy path "() {
		given: 
			User u = new User("1234","bbb",Role.PAGE_1);
		when: 
			boolean b = u.authenticate("bbb");
	    then:
			b == true;
	}
	
	def "Check that login works: bad path "() {
		given: 
			User u = new User("1234","bbb",Role.PAGE_1);
		when: 
			boolean b = u.authenticate("aaa");
	    then:
			b == false;
	}
	
	def "Check that regular user is not admin"() {
		when: 
			User u = new User("1234", "bbb", Role.PAGE_2);
	    then:
	    	u.isAdmin() == false;
	}
	
	
	def "Check that admin user is admin when setting new role admin"() {
		given:
			User a = new User("1234", "bbb", Role.PAGE_2)
		when: 
			def roles = [ Role.ADMIN ]
			a.setRoles(roles)
	    then:
			a.hasRole(Role.ADMIN) == true
			a.isAdmin() == true
	}
	
	
	def "Check that equals method works if equal"() {
		given: 
			User u1 = new User("1234","aaa",User.Role.PAGE_1);
		when: 
			User u2 = new User("1234", "bbb", User.Role.PAGE_2);
	    then:
			u1.equals(u2);
	}
	
	def "Check that equals method works if different"() {
		given: 
			User u1 = new User("1234","aaa",Role.PAGE_1);
		when: 
			User u2 = new User("12345", "bbb", Role.PAGE_1);
	    then:
			!u1.equals(u2);
	}
	
	def "Method toString of user should return correct representation of object"() {
		when:
			User u1 = new User("perrito","bbb",Role.PAGE_1,Role.PAGE_2)
		then:
		   "User [username=perrito, roles=[page1] [page2] ]".equals(u1.toString());
	}
	
}
