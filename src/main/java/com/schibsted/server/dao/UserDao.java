package com.schibsted.server.dao;

import java.util.List;

import com.schibsted.server.domain.User;
import com.schibsted.server.exception.UserExistsException;
import com.schibsted.server.exception.UsernameNotFoundException;

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
	 * @throws UsernameNotFoundException
	 *             if the username cannot be found
	 */
	boolean delete(User u) throws UsernameNotFoundException;

	/**
	 * Adds a new user
	 * 
	 * @param u
	 *            the new user to be added
	 * @throws UserExistsException
	 *             if the same username exists already
	 */
	void add(User u) throws UserExistsException;

	/**
	 * Updates an existing user
	 * 
	 * @param u
	 *            the user to be updated
	 * @throws UsernameNotFoundException
	 *             if the username cannot be found
	 */
	void update(User u) throws UsernameNotFoundException;
}
