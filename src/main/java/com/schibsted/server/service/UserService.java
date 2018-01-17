package com.schibsted.server.service;

import java.util.List;

import com.schibsted.server.domain.User;
import com.schibsted.server.domain.User.Role;
import com.schibsted.server.exception.UserExistsException;
import com.schibsted.server.exception.UsernameNotFoundException;

/**
 * Business service interface to handle communication between web and
 * persistence layer
 * 
 * @author <a href="mailto:oscarfernando.perez@gmail.com">Oscar Perez</a>
 */
public interface UserService {

	/**
	 * Retrieves a user by username.
	 * 
	 * @param username
	 *            the username for the user
	 * @return User a populated user object, null if nothing is found
	 */
	User getUserByUsername(String username);

	/**
	 * Retrieves a list of all users.
	 * 
	 * @return List
	 */
	List<User> getUsers();

	/**
	 * Checks whether a user has a certain role
	 * 
	 * @param username
	 *            username for the user
	 * @param role
	 *            the role to be checked
	 * @return true if the user has this role, false otherwise
	 */
	boolean hasUserRole(String username, Role role);

	/**
	 * Tries to authenticate the user against the system
	 * 
	 * @param username
	 *            username to be checked
	 * @param password
	 *            password to be checked
	 * @return true if authentication succeeds, false otherwise
	 */
	boolean checkCredentials(String username, String password);

	/**
	 * Deletes a user from the database by their username
	 * 
	 * @param username
	 *            the username
	 * @return true if deleted, false otherwise
	 * @throws UserNameNotFoundException
	 *             exception thrown when user not found
	 */
	boolean delete(String username) throws UsernameNotFoundException;

	/**
	 * Adds a new a user.
	 * 
	 * @param user
	 *            the user's information
	 * @throws UserExistsException
	 *             thrown when user already exists
	 * @return user the new user object
	 * @throws UserNameNotFoundException
	 *             exception thrown when user not found
	 */
	void create(User user) throws UserExistsException;

	/**
	 * Adds a new a user.
	 * 
	 * @param user
	 *            the user's information
	 * @throws UserNotFoundException
	 *             thrown if username of this user cannot not found
	 * @return user the new user object
	 * @throws UserNameNotFoundException
	 *             exception thrown when user not found
	 */
	void update(User user) throws UsernameNotFoundException;

}
