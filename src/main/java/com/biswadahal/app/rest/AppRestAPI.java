package com.biswadahal.app.rest;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biswadahal.app.rest.resources.Hello;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.google.inject.Injector;

public class AppRestAPI extends ResourceConfig {
	public static final Logger logger = LoggerFactory.getLogger(AppRestAPI.class);
	
	@Inject
	public AppRestAPI(ServiceLocator serviceLocator, ServletContext servletContext) {
		logger.info("Initializing Rest API Application");
		Injector guiceInjector = (Injector) servletContext.getAttribute(Injector.class.getName());
        if (guiceInjector == null) {
        	logger.error("Guice Injector was not found in servlet context when trying to bridge guice into HK2");
        	throw new RuntimeException("Config Exception - Guice Injector not found");
        }
		
		bridgeGuiceIntoHK2(serviceLocator, guiceInjector);
		registerObjectMapper(guiceInjector.getInstance(ObjectMapper.class));
		addResources();

	}

	private void bridgeGuiceIntoHK2(ServiceLocator serviceLocator, Injector guiceInjector) {
		logger.trace(String.format("Briding guice into hk2 using servicelocator %s, and guiceInjector: %s", serviceLocator, guiceInjector));
		GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
        GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
        guiceBridge.bridgeGuiceInjector(guiceInjector);
	}
	
	private void registerObjectMapper(ObjectMapper om){
		logger.trace(String.format("Configuring RestAPI object mapper: %s", om));
		JacksonJaxbJsonProvider jsonProvider = new JacksonJaxbJsonProvider();
		jsonProvider.setMapper(om);
		register(jsonProvider);
	}
	private void addResources() {
		register(Hello.class);
	}
}
