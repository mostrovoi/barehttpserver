package com.schibsted.server.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.schibsted.server.dao.UserDao;
import com.schibsted.server.domain.User;
import com.schibsted.server.domain.User.Role;
import com.schibsted.server.exception.UserExistsException;
import com.schibsted.server.exception.UsernameNotFoundException;
import com.schibsted.server.exception.ValidationException;

public class UserDaoImpl implements UserDao {

	private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
	private final Map<String, User> users;

	public UserDaoImpl() {
		users = new HashMap<>();
		initUsers();
	}

	private void initUsers() {
		try {
			users.put("user1", new User("user1", "user1", Role.PAGE_1));
			users.put("user2", new User("user2", "user2", Role.PAGE_2));
			users.put("user3", new User("user3", "user3", Role.PAGE_3));
			users.put("user4", new User("user4", "user4", Role.PAGE_1, Role.PAGE_2, Role.PAGE_3));
			users.put("user5", new User("user5", "user5", Role.PAGE_2, Role.PAGE_3));
			users.put("admin", new User("admin", "admin", Role.ADMIN));
		} catch (ValidationException e) {
			logger.error("Error initializing users. This should never happen");
			throw new IllegalArgumentException();
		}

	}

	@Override
	public List<User> getAll() {
		return new ArrayList<User>(users.values());
	}

	@Override
	public User get(String username) {
		User u = users.get(username);
		if (u == null)
			logger.warn("Username {} not found", username);
		return u;
	}

	@Override
	public boolean delete(User u) throws UsernameNotFoundException {
		checkIfExists(u);
		User deletedUser = users.remove(u.getUsername());
		return (deletedUser != null);
	}

	@Override
	public void add(User u) throws UserExistsException {
		if (this.get(u.getUsername()) != null)
			throw new UserExistsException("User with username " + u.getUsername() + " already exists");
		else
			users.put(u.getUsername(), u);
	}

	@Override
	public void update(User u) throws UsernameNotFoundException {
		checkIfExists(u);
		users.put(u.getUsername(), u);
	}

	private void checkIfExists(User u) throws UsernameNotFoundException {
		if (u == null || this.get(u.getUsername()) == null)
			throw new UsernameNotFoundException("User with username " + u.getUsername() + " not found");
	}
}
