package com.zandero.server.guice;

import com.google.inject.AbstractModule;
import com.zandero.server.rest.BaseRest;

/**
 *
 */
public class BaseRestModule extends AbstractModule {

	@Override
	protected void configure() {

		bind(BaseRest.class);
	}
}
