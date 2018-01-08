package com.schibsted.server.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.schibsted.server.domain.User;
import com.schibsted.server.domain.User.Role;

public class UserRepository {

	  private Map<String, User> users = null;

	  UserRepository() {
	    users = new HashMap<String, User>();
	    initUsers();
	  }

	  private void initUsers() {
	    users.put("user1",  new User("user1", "user1", Role.PAGE_1));
	    users.put("user2", new User("user2", "user2", Role.PAGE_2));
	    users.put("user3", new User("user3", "user3", Role.PAGE_3));
	    users.put("user4",  new User("user4", "user4", Role.PAGE_1,Role.PAGE_2, Role.PAGE_3));
	    users.put("user5",  new User("user5", "user5", Role.PAGE_2, Role.PAGE_3));
	    users.put("admin", new User("admin", "admin", Role.ADMIN));
	  }

	  Map<String, User> getRepository() {
	    return users;
	  }


}
