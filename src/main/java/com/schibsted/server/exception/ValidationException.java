package com.schibsted.server.exception;

/**
 * Checked exception thrown when an input value is not valid
 *
 * @author <a href="mailto:oscarfernando.perez@gmail.com">Oscar Perez</a>
 */
public class ValidationException extends Exception {

	private static final long serialVersionUID = 4985801364946788322L;

	/**
	 * Constructor for ValidationException.
	 *
	 * @param message
	 *            exception message
	 */
	public ValidationException(final String message) {
		super(message);
	}
}