package com.zandero.server.guice;

import com.google.inject.AbstractModule;
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

		bind(RequestContext.class).to(BackendRequestContext.class);
		bind(SessionService.class).to(SessionServiceImpl.class);
	}
}
