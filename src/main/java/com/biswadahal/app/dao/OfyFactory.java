package com.biswadahal.app.dao;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biswadahal.app.models.HelloEntity;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;

@Singleton
public class OfyFactory extends ObjectifyFactory{
	public static final Logger logger = LoggerFactory.getLogger(OfyFactory.class);
	
	private Injector injector;
	
	private void registerEntities(){
		logger.trace("Register Objectify Entities");
		this.register(HelloEntity.class);
	}


	@Inject
	public OfyFactory(Injector injector){
		this.injector = injector;
		registerEntities();
	}
	
	@Override
	public <T> T construct(Class<T> type) {
		return injector.getInstance(type);
	}

	@Override
	public Objectify begin() {
		return new Ofy(this);
	}
	
	
}
