package com.schibsted.server.domain;

import java.util.ArrayList;  
import java.util.Arrays;
import java.util.List;

import com.schibsted.server.exception.ValidationException;

public class User {

	private final static String VALID_USERNAME_PATTERN  = "^[a-z0-9]+$";
	
	public enum Role {
	    PAGE_1("page1"),
	    PAGE_2("page2"),
	    PAGE_3("page3"),
	    ADMIN("admin");
		
		private final String name;       

	    private Role(String s) {
	        name = s;
	    }
		public String toString() {
			return this.name;
		}
	}
	
	private final String username;
	private final List<Role> roles = new ArrayList<Role>();
	private String password;
	
    public User(String username, String password) throws ValidationException {
    	validateUsername(username);
        this.username = username;
        this.setPassword(password);
    }  
	
    public User(String username, String password, Role... roles) throws ValidationException {
        this(username,password);
    	if(roles != null) 
	        this.roles.addAll(Arrays.asList(roles));
    }

	public String getUsername() {
		return username;
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
		this.roles.clear();
		if(roles != null ) 
			this.roles.addAll(roles);
	}
	
	public void setPassword(String password) throws ValidationException {
		if(password == null)
			throw new ValidationException("Null password is not valid");
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
	
	private void validateUsername(String username) throws ValidationException {
    	if(username == null  || !username.matches(VALID_USERNAME_PATTERN))
    		throw new ValidationException("Username not valid:"+username);
	}
	
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
		StringBuffer sb = new StringBuffer();
		if(roles != null) {
			for(Role r : roles){
				sb.append("["+r.toString()+"] ");
			}
		}
		else 
			sb.append("-");
		return "User [username=" + username + ", roles=" + sb.toString() + "]";
	}
	
}
