package com.schibsted.server.dao;

import spock.lang.Specification

import com.schibsted.server.domain.User

import static com.schibsted.server.domain.User.Role

import com.schibsted.server.dao.UserDao
import com.schibsted.server.dao.impl.UserDaoImpl
import com.schibsted.server.exception.UsernameNotFoundException
import com.schibsted.server.exception.UserExistsException
import com.schibsted.server.exception.ValidationException;

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
	
	def "A new user with invalid username throws exception"() {
		when:
			User u = new User(username, password)
			userDao.add(u)
		then:
			thrown(expectedException)
		where:
		username  |  password || expectedException
		  "AAA"   |   "bbb"   || ValidationException
		  "%fdas" |   "bbb"   || ValidationException
		  null    |   "bbb"   || ValidationException
		  "aaa"   |   null    || ValidationException
	 }

	def "Adding one user to db should increase db size in one"() {
		given:
			User u = new User("123","aaa",null)
			def size = userDao.getAll().size()
		when:
			userDao.add(u)
		then:	
			userDao.getAll().size() == size+1
	}
	
	def "Password should be updated in DB"() {
		given:
			User u = new User("perrito","bravo")
			userDao.add(u)
		when:
			u.setPassword("nuevopasssuperseguro")
	  	    userDao.update(u)
		then:
			userDao.get(u.getUsername()).authenticate("nuevopasssuperseguro")
	}

	def "2 new users with same username should only create 1 user when using update"() {
		given:
			User u = new User("usertest1", "user1")
			User u2 = new User("usertest1", "xxxx")
			userDao.add(u)
		when:
			userDao.update(u2);
		then:
			userDao.get(u.getUsername()).authenticate("xxxx")
	}

	def "Delete one user should return null "() {
		given:
			User u = new User("a","b")
			userDao.add(u)
		when:
			userDao.delete(u)
		then:
			userDao.get(u.getUsername()) == null
	}

	def "Adding 1 user with username already existing should throw UserExistException"() {
		given:
			User u = new User("a","b")
			User u2 = new User("a","c")
			userDao.add(u)
		when:
			userDao.add(u2)
		then:
			thrown UserExistsException
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
		given:
			User u = new User("aa","ccc",Role.ADMIN)
			userDao.add(u)
		when:
			def roles = [Role.PAGE_1,Role.PAGE_2]
			u.setRoles(roles)
			userDao.update(u)
		then:
			userDao.get(u.getUsername()).getRoles().contains(Role.PAGE_1)
			userDao.get(u.getUsername()).getRoles().size() == 2
	}
}
