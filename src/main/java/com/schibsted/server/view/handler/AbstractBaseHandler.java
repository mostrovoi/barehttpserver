package com.schibsted.server.view.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.net.httpserver.HttpHandler;

/**
 * All common operations should be moved here. Now are in HttpExchangeUtil since filters use these too
 * @author operezdo
 *
 */
abstract class AbstractBaseHandler implements HttpHandler {

	static final Logger logger = LogManager.getLogger(AbstractBaseHandler.class);
	    
}
