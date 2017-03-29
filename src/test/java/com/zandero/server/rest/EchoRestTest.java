package com.zandero.server.rest;

import com.zandero.server.rest.test.GuiceTest;
import org.apache.http.HttpStatus;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class EchoRestTest extends GuiceTest {

	@Test
	public void echoTest() {

		// no session ...
		Response response = new ResteasyClientBuilder()
			.build()
			.target(ROOT_URL + "/rest/echo")
			.request()
			.get();

		assertEquals(HttpStatus.SC_OK, response.getStatus());
	}
}
