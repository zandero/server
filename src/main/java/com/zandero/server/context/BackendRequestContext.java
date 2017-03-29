package com.zandero.server.context;

import com.zandero.rest.context.BaseRequestContext;
import com.zandero.server.entities.User;
import com.zandero.server.service.SessionService;
import org.jboss.resteasy.plugins.guice.RequestScoped;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 *
 */
@RequestScoped
public class BackendRequestContext extends BaseRequestContext {

	public static final String SESSION_HEADER = "X-SessionId";

	private User user;

	@Inject
	public BackendRequestContext(HttpServletRequest servletRequest,
	                             SessionService sessionService) {

		super(servletRequest);

		String sessionId = getHeader(SESSION_HEADER);

		if (sessionId != null) {
			user = sessionService.get(sessionId);
		}
	}

	@Override
	public Principal getUserPrincipal() {

		return () -> user != null ? user.getUsername() : null;
	}

	@Override
	public boolean isUserInRole(String role) {

		if (user == null) {
			return false;
		}

		return user.isInRole(role);
	}

	@Override
	public boolean isSecure() {

		return false;
	}

	@Override
	public String getAuthenticationScheme() {

		return null;
	}

	public User getUser() {

		return user;
	}
}
