package com.zandero.server.rest;

import com.zandero.server.context.BackendRequestContext;
import com.zandero.server.entities.User;
import com.zandero.server.entities.json.UserJSON;
import com.zandero.server.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

/**
 * Simple REST API to test RestFiltering ...
 * each REST call is for a different test
 */

@Path("/user")
@Singleton
public class BaseRest {

	private final static Logger log = LoggerFactory.getLogger(BaseRest.class);

	private final Provider<BackendRequestContext> ctxProvider;

	private final SessionService sessions;

	@Inject
	public BaseRest(SessionService sessionService,
	                Provider<BackendRequestContext> contextProvider) {

		sessions = sessionService;
		ctxProvider = contextProvider;
	}

	/**
	 * Returns logged in user if any
	 *
	 * @return 200 with user or 204 if no user is logged in
	 */
	@GET
	@Path("/info")
	@Produces(MediaType.APPLICATION_JSON)
	public Response info() {

		User user = ctxProvider.get().getUser();
		if (user == null) {
			log.info("User not logged in ... ");
			return Response.noContent().build();
		}

		return Response.ok(new UserJSON(user)).build();
	}

	/**
	 * Log in user with username and password
	 *
	 * @param username username
	 * @param password password
	 * @return logged in user with session cookie, or throws exception in case username or password is invalid
	 */
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@QueryParam("username") String username, @QueryParam("password") String password) {

		String sessionId = sessions.login(username, password);
		User user = sessions.get(sessionId);

		log.info("Login of: '" + user.getFullName() + "' successful!");
		return Response.ok(new UserJSON(user))
			.type(MediaType.APPLICATION_JSON)
			.cookie(new NewCookie(BackendRequestContext.SESSION_HEADER, sessionId))
			.build();
	}

	/**
	 * Dummy REST to check Admin access / User should not have access to this REST
	 *
	 * @return 200 if access granted
	 */
	@RolesAllowed("Admin")
	@GET
	@Path("/admin")
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkAdmin() {
		// checks if admin has access
		return Response.ok().build();
	}
}
