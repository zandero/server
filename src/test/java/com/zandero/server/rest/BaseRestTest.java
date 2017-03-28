package com.zandero.server.rest;

import com.zandero.rest.RestException;
import com.zandero.server.context.BackendRequestContext;
import com.zandero.server.entities.UserRole;
import com.zandero.server.entities.json.UserJSON;
import com.zandero.server.rest.test.GuiceTest;
import org.apache.http.HttpStatus;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.junit.Test;

import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 *
 */
public class BaseRestTest extends GuiceTest {

	@Test
	public void loginTest() {

		// no session ...
		Response response = new ResteasyClientBuilder()
			.build()
			.target(ROOT_URL + "/rest/user/info")
			.request()
			.get();

		assertEquals(HttpStatus.SC_NO_CONTENT, response.getStatus());

		// login
		response = new ResteasyClientBuilder()
			.build()
			.target(ROOT_URL + "/rest/user/login?username=admin&password=password")
			.request()
			.post(null);

		assertEquals(HttpStatus.SC_OK, response.getStatus());

		UserJSON user = response.readEntity(UserJSON.class);
		assertNotNull(user);
		assertEquals("The Admin", user.name);
		assertEquals("admin", user.userName);
		assertEquals(UserRole.Admin, user.role);
		assertTrue(user.role.isAllowed(UserRole.User));

		// check cookie ... session
		NewCookie sessionCookie = response.getCookies().get(BackendRequestContext.SESSION_HEADER);
		assertNotNull(sessionCookie);
		String sessionId = sessionCookie.getValue();


		// use cookie in call to /info
		response = new ResteasyClientBuilder()
			.build()
			.target(ROOT_URL + "/rest/user/info")
			.request()
			.header(BackendRequestContext.SESSION_HEADER, sessionId)
			.get();

		assertEquals(HttpStatus.SC_OK, response.getStatus());

		// same user should be returned
		user = response.readEntity(UserJSON.class);
		assertNotNull(user);
		assertEquals("The Admin", user.name);
		assertEquals("admin", user.userName);
		assertEquals(UserRole.Admin, user.role);
		assertTrue(user.role.isAllowed(UserRole.User));
	}

	@Test
	public void userTest() {

		// login
		Response response = new ResteasyClientBuilder()
			.build()
			.target(ROOT_URL + "/rest/user/login?username=user&password=password")
			.request()
			.post(null);

		assertEquals(HttpStatus.SC_OK, response.getStatus());

		// check cookie ... session
		NewCookie sessionCookie = response.getCookies().get(BackendRequestContext.SESSION_HEADER);
		assertNotNull(sessionCookie);
		String sessionId = sessionCookie.getValue();

		// try accessing /admin REST ... should not be granted
		response = new ResteasyClientBuilder()
			.build()
			.target(ROOT_URL + "/rest/user/private")
			.request()
			.header(BackendRequestContext.SESSION_HEADER, sessionId)
			.get();

		assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatus());

		// get error
		RestException exception = response.readEntity(RestException.class);
		assertEquals("Access forbidden: role not allowed", exception.getMessage());
		assertEquals(HttpStatus.SC_FORBIDDEN, exception.getCode());
	}

	@Test
	public void adminTest() {

		// login
		Response response = new ResteasyClientBuilder()
			.build()
			.target(ROOT_URL + "/rest/user/login?username=admin&password=password")
			.request()
			.post(null);

		assertEquals(HttpStatus.SC_OK, response.getStatus());

		// check cookie ... session
		NewCookie sessionCookie = response.getCookies().get(BackendRequestContext.SESSION_HEADER);
		assertNotNull(sessionCookie);
		String sessionId = sessionCookie.getValue();

		// try accessing /admin REST ... should not be granted
		response = new ResteasyClientBuilder()
			.build()
			.target(ROOT_URL + "/rest/user/private")
			.request()
			.header(BackendRequestContext.SESSION_HEADER, sessionId)
			.get();

		// access is granted
		assertEquals(HttpStatus.SC_OK, response.getStatus());
	}

	@Test
	public void restNotFoundTest() {

		// login
		Response response = new ResteasyClientBuilder()
			.build()
			.target(ROOT_URL + "/rest/user/missing")
			.request()
			.get();

		assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatus());

		// get error
		RestException exception = response.readEntity(RestException.class);
		assertEquals("RESTEASY003210: Could not find resource for full path: http://localhost:4444/rest/user/missing", exception.getMessage());
		assertEquals(HttpStatus.SC_NOT_FOUND, exception.getCode());
	}
}
