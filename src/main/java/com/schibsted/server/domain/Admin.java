package com.schibsted.server.domain;

public class Admin extends User {
	
	public Admin(String userName, String password, Role... roles) {
		super(userName, password, roles);
	}

	@Override
	public boolean isAdmin() {
		return true;
	}

}
