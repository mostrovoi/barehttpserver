package com.schibsted.server.dao;

import java.util.List;

import com.schibsted.server.domain.User;
import com.schibsted.server.exception.UserExistsException;
import com.schibsted.server.exception.UsernameNotFoundException;
import com.schibsted.server.exception.ValidationException;

public interface UserDao {

	/**
	 * Gets a list of all users
	 *
	 * @return List populated list of users
	 */
	List<User> getAll();

	/**
	 * Returns a user based on her username
	 * 
	 * @param username
	 *            to be looked up
	 * @return User entity containing all data associated to user
	 */
	User get(String username);

	/**
	 * Deletes a user
	 * 
	 * @param u
	 *            the user to be deleted
	 * @return true if deleted, false if not found or failed to delete
	 */
	boolean delete(User u);

	/**
	 * Adds a new user
	 * 
	 * @param u
	 *            the new user to be added
	 * @throws UserExistsException
	 *             if the same username exists already
	 * @throws ValidationException
	 *             if the username is not valid or has not set username, password
	 */
	void add(User u) throws UserExistsException, ValidationException;

	/**
	 * Updates an existing user
	 * 
	 * @param u
	 *            the user to be updated
	 * @throws UsernameNotFoundException
	 *             if the username cannot be found
	 * @throws ValidationException
	 *             if has not set username or password
	 */
	void update(User u) throws UsernameNotFoundException, ValidationException;
}
