package com.zandero.server;

import com.google.inject.AbstractModule;
import com.zandero.cmd.CommandBuilder;
import com.zandero.cmd.CommandLineParser;
import com.zandero.cmd.option.CommandOption;
import com.zandero.cmd.option.IntOption;
import com.zandero.server.guice.BaseRestModule;
import com.zandero.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Jetty set up with Guice and RestEasy
 */
public class Main {

	private static final Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws Exception {

		CommandOption port = new IntOption("p")
			.longCommand("port")
			.setting("port")
			.setDefault(4444);

		CommandBuilder builder = new CommandBuilder();
		builder.add(port);

		CommandLineParser parser = new CommandLineParser(builder);

		Settings settings = parser.parse(args);

		// GO ON ... create server and start
		try {

			AbstractModule[] modules = new AbstractModule[] {
				new org.jboss.resteasy.plugins.guice.ext.RequestScopeModule(), // bind appropriate RequestScope
				new BaseRestModule()
			};

			// Start the server
			WebServer.init(settings, modules);

			WebServer.start();

			System.out.println("Server STARTED");
			System.out.println("Listening on port: " + settings.getInt("port"));

			// Wait until the server exits
			WebServer.join();
		}
		catch (Exception e) {

			log.error("Unhandled exception: ", e);
			System.out.println(e.getMessage());
		}

		// stop gracefully
		WebServer.stop();

		System.out.println("Server STOPPED");

		System.exit(0);
	}
}
