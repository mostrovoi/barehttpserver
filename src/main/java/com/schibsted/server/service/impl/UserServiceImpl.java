package com.schibsted.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.schibsted.server.domain.User;
import com.schibsted.server.domain.User.Role;
import com.schibsted.server.service.UserService;

public class UserServiceImpl implements UserService {

	private final UserRepository users;
	
	public UserServiceImpl() {
		users = new UserRepository();
	}
	
	// returns null if not found ?
	public User getUserByUsername(String username) {
		 return users.getRepository().get(username);
	}

	public List<User> getUsers() {
		return new ArrayList<User>(users.getRepository().values());
	}


	public boolean doLogin(String username, String password) {
		User user = this.getUserByUsername(username);
		return (user != null && user.authenticate(password));
	}

	public boolean isUserAuthorized(String username, Role role) {
		User user = this.getUserByUsername(username);
		if (user != null && user.hasRole(role))
			return true;
		else
			return false;
	}

}
