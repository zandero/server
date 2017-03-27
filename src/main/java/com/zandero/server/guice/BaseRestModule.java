package com.zandero.server.guice;

import com.google.inject.AbstractModule;
import com.zandero.rest.*;
import com.zandero.rest.context.AuthorizationFilter;
import com.zandero.rest.context.RequestContext;
import com.zandero.server.context.BackendRequestContext;
import com.zandero.server.rest.BaseRest;
import com.zandero.server.service.SessionService;
import com.zandero.server.service.SessionServiceImpl;

/**
 *
 */
public class BaseRestModule extends AbstractModule {

	@Override
	protected void configure() {

		bind(BaseRest.class);

		bind(AuthorizationFilter.class);

		bind(RestExceptionMapper.class); // mandatory for exception event filtering
		bind(RestRequestEventFilter.class); // optional in case request duration log should be added

		bind(RestResponseEventFilter.class); // mandatory for event filtering
		bind(RestEventThreadPool.class).to(RestEventThreadPoolImpl.class);

		bind(RequestContext.class).to(BackendRequestContext.class);
		bind(SessionService.class).to(SessionServiceImpl.class);
	}
}
