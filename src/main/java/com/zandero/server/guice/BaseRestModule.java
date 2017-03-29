package com.zandero.server.guice;

import com.google.inject.AbstractModule;
import com.zandero.rest.*;
import com.zandero.rest.context.AuthorizationFilter;
import com.zandero.rest.context.RequestContext;
import com.zandero.server.context.BackendRequestContext;
import com.zandero.server.rest.BaseRest;
import com.zandero.server.rest.EchoRest;
import com.zandero.server.service.SessionService;
import com.zandero.server.service.SessionServiceImpl;

/**
 *
 */
public class BaseRestModule extends AbstractModule {

	@Override
	protected void configure() {

		// Register RESTs
		bind(BaseRest.class);
		bind(EchoRest.class);

		// Register security filter
		bind(AuthorizationFilter.class);

		// Register exception handling
		bind(RestExceptionMapper.class); // mandatory for exception event filtering
		bind(RestRequestEventFilter.class); // optional in case request duration log should be added

		// Register async event handling and thread pool
		bind(RestResponseEventFilter.class); // mandatory for event filtering
		bind(RestEventThreadPool.class).to(RestEventThreadPoolImpl.class);

		// Register request scoped context
		bind(RequestContext.class).to(BackendRequestContext.class);

		// Register all other services
		bind(SessionService.class).to(SessionServiceImpl.class);
	}
}
