package com.schibsted.server.exception;

/**
 * Checked exception thrown when a username is not found in the database
 *
 * @author <a href="mailto:oscarfernando.perez@gmail.com">Oscar Perez</a>
 */
public class UsernameNotFoundException extends Exception {

	private static final long serialVersionUID = -5766983635607848703L;

	/**
	 * Constructor for UserExistsException.
	 *
	 * @param message
	 *            exception message
	 */
	public UsernameNotFoundException(final String message) {
		super(message);
	}
}