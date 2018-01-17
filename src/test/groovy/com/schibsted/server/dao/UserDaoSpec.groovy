package com.schibsted.server.dao;

import spock.lang.Specification
import com.schibsted.server.domain.User
import static com.schibsted.server.domain.User.Role

import com.schibsted.server.dao.UserDao
import com.schibsted.server.dao.impl.UserDaoImpl
import com.schibsted.server.exception.UsernameNotFoundException

class UserDaoSpec extends Specification {

	private UserDao userDao;

	def setup() {
		userDao = new UserDaoImpl();
	}

	def "A new user should get inserted in DB"() {
		given:
			User u = new User("ramon","bbb",null)
		when:
			userDao.add(u)
		then:
			"ramon".equals(userDao.get("ramon").getUsername())
	}

	def "Password should be updated in DB"() {
		given:
			User u = new User("perrito","bravo")
			userDao.add(u)
		when:
			u.setPassword("nuevopasssuperseguro")
	  	    userDao.update(u)
		then:
			userDao.get(u.getUsername()).getPassword() == "user1"
	}

	def "Create 2 users with same username and update should only create 1 user"() {
	}

	def "Delete one user should return null "() {
	}

	def "Add 1 user with username already existing should throw UserExistException"() {
	}

	def "Update one user with non existing username in DB should throw UsernameNotFoundException"() {
		given:
			User u = new User("perrito","bravo")
		when:
			userDao.update(u)
		then:
			thrown UsernameNotFoundException
	}

	def "Updating roles for one user should return updated roles"() {
	}
}
