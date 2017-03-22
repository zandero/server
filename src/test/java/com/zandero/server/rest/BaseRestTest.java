package com.zandero.server.rest;

import com.zandero.server.entities.UserRole;
import com.zandero.server.entities.json.UserJSON;
import com.zandero.server.rest.test.GuiceTest;
import org.apache.http.HttpStatus;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.junit.Test;

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
			.target(ROOT_URL + "/rest/user/login?username=lojz&password=password")
			.request()
			.post(null);

		assertEquals(HttpStatus.SC_OK, response.getStatus());

		UserJSON user = response.readEntity(UserJSON.class);
		assertNotNull(user);
		assertEquals("Lojze Lojzasti", user.name);
		assertEquals("lojz", user.userName);
		assertEquals(UserRole.Admin, user.role);
		assertTrue(user.role.isAllowed(UserRole.User));

		// check cookie ... session

	}
}
