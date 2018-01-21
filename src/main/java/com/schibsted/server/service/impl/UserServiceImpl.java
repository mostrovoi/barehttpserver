package com.schibsted.server.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.server.dao.UserDao;
import com.schibsted.server.dao.impl.UserDaoImpl;
import com.schibsted.server.domain.User;
import com.schibsted.server.domain.User.Role;
import com.schibsted.server.exception.UserExistsException;
import com.schibsted.server.exception.UsernameNotFoundException;
import com.schibsted.server.exception.ValidationException;
import com.schibsted.server.service.UserService;

public class UserServiceImpl implements UserService {

	private final UserDao users;
	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

	public UserServiceImpl() {
		users = new UserDaoImpl();
	}

	@Override
	public User getUserByUsername(String username) {
		return users.get(username);
	}

	@Override
	public List<User> getUsers() {
		return users.getAll();
	}

	@Override
	public boolean checkCredentials(String username, String password) {
		User user = this.getUserByUsername(username);
		return (user == null) ? false : user.authenticate(password); 
	}

	@Override
	public boolean hasUserRole(String username, Role role) {
		User user = this.getUserByUsername(username);
		return (user == null) ? false : user.hasRole(role); 
	}

	@Override
	public boolean delete(String username) {
		User user = this.getUserByUsername(username);
		return users.delete(user);
	}

	@Override
	public void create(User user) throws UserExistsException, ValidationException {
		users.add(user);
	}

	@Override
	public void update(User user) throws UsernameNotFoundException, ValidationException {
		users.update(user);
	}

}
