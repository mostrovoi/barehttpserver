package com.schibsted.server.service;

import java.util.List;

import com.schibsted.server.domain.User;
import com.schibsted.server.domain.User.Role;

public interface UserService {

	User getUserByUsername(String username);

	List<User> getUsers();

	boolean isUserAuthorized(String username, Role role);

	boolean doLogin(String username, String password);

}
