package com.schibsted.server.service;

/**
* Checked exception thrown when a username exists already
*
* @author <a href="mailto:oscarfernando.perez@gmail.com">Oscar Perez</a>
*/
public class UserExistsException extends Exception {

	private static final long serialVersionUID = -5115223072626536840L;

   /**
    * Constructor for UserExistsException.
    *
    * @param message exception message
    */
   public UserExistsException(final String message) {
       super(message);
   }
}