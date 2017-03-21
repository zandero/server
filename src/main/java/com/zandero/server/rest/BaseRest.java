package com.zandero.server.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Simple REST API to test RestFiltering ...
 * each REST call is for a different test
 */

@Path("/test")
@Singleton
public class BaseRest {

	private final static Logger log = LoggerFactory.getLogger(BaseRest.class);

	@Inject
	public BaseRest() {

	}

	/**
	 * Simple rest echo
	 *
	 * @return 200 echo
	 */
	@GET
	@Path("/echo")
	@Produces(MediaType.APPLICATION_JSON)
	public String ping() {

		return "echo";
	}
}
