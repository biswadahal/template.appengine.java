package com.biswadahal.app.guice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biswadahal.app.dao.OfyService;
import com.biswadahal.app.rest.resources.Hello;
import com.biswadahal.app.services.HelloService;
import com.biswadahal.app.services.HelloServiceHelper;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import com.googlecode.objectify.ObjectifyFilter;

public class AppServletModule extends ServletModule {
	public static final Logger logger = LoggerFactory.getLogger(AppServletModule.class);

	@Override
	public void configureServlets() {
		super.configureServlets();

		bindObjectMapper();
		bindObjectify();
		bindBizObjects();
		bindJerseyResources();
		logger.info(String.format("Guice module configured: %s", this.getClass().getName()));
	}

	private void bindObjectMapper() {
		logger.trace("Bind jackson object mapper");
		ObjectMapper om = new ObjectMapper();
		om.setSerializationInclusion(Include.ALWAYS)
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true)
			.configure(SerializationFeature.INDENT_OUTPUT, true)
			.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
			.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		bind(ObjectMapper.class).toInstance(om);
		logger.trace(String.format("Bounded guice object mapper: %s", om));
	}

	private void bindObjectify() {
		logger.trace("Bind objectify");
		filter("/*").through(ObjectifyFilter.class);
		bind(ObjectifyFilter.class).in(Singleton.class);
		requestStaticInjection(OfyService.class);
	}

	private void bindBizObjects() {
		logger.trace("Bind models");
		bind(HelloService.class);
		bind(HelloServiceHelper.class);
	}

	private void bindJerseyResources() {
		logger.trace("Bind jersey resoures");
		bind(Hello.class);
	}

}
