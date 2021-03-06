package com.biswadahal.app.guice;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceServletContextListener;

public class AppGuiceServletContextListener extends GuiceServletContextListener {
	public static final Logger logger = LoggerFactory.getLogger(AppGuiceServletContextListener.class);

	@Override
	protected Injector getInjector() {
		Module[] modules = new Module[]{new AppServletModule()};
		logger.info("Initialized Injector for module: " + Arrays.asList(modules));
		return Guice.createInjector(modules);
	}
	
}
