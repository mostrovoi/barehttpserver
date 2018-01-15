package com.schibsted.server.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User {

	public enum Role {
	    PAGE_1,
	    PAGE_2,
	    PAGE_3,
	    ADMIN
	}
	
	private String username;
	private List<Role> roles;
	private String password;
	
    public User(String userName, String password, Role... roles) {
        this.username = userName;
        this.password = password;
        this.roles = new ArrayList<Role>();
        this.roles.addAll(Arrays.asList(roles));
    }

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<Role> getRoles() {
		return roles;
	}
	
	/**
	 * This custom setter avoids the usage of null in roles
	 * Thus, working the same way than in the constructor
	 * 
	 * @param roles
	 */
	public void setRoles(List<Role> roles) {
		if(roles == null )
			this.roles.clear();
		else
			this.roles = roles;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isAdmin() {
		return hasRole(Role.ADMIN);
	}
	
	public boolean hasRole(Role role){
		return this.roles.contains(role) ? true : false;
	}
	
	public boolean authenticate(String password) {
		if (this.password != null && this.password.equals(password))
			return true;
		else
			return false;
	}
	
	/**
	 * {@inheritDoc}
	 * hashCode method based on the assumption that username is unique
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	

	/**
	 * {@inheritDoc}
	 * Equals method based on the assumption that username is unique
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "User [username=" + username + ", roles=" + roles + "]";
	}
	
}
