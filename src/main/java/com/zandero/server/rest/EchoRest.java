package com.zandero.server.rest;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 */
@Singleton
@Path("/")
public class EchoRest {

	/**
	 * Returns logged in user if any
	 *
	 * @return 200 with user or 204 if no user is logged in
	 */
	@GET
	@Path("/echo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response echo() {

		return Response.ok("{\"data\": \"Hello world!\"").build();
	}
}
