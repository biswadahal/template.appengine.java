package com.biswadahal;

import java.lang.reflect.Field;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.biswadahal.app.dao.OfyFactory;
import com.biswadahal.app.guice.AppServletModule;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalTaskQueueTestConfig;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.ServletModule;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;

public class AppEngineTests {

	private final LocalServiceTestHelper aeHelper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig(),
			new LocalMemcacheServiceTestConfig(), new LocalTaskQueueTestConfig());
	
	protected Closeable session;
	protected Injector injector;
	
	@BeforeSuite(alwaysRun=true)
	public void init(){
		ServletModule module = new AppServletModule();
		injector = Guice.createInjector(module);
		System.out.println(injector.getAllBindings());
		OfyFactory factory = new OfyFactory(injector);
		ObjectifyService.setFactory(factory);
	}
	
	@BeforeMethod(alwaysRun=true)
	public void beforeMethod(){
		session = ObjectifyService.begin();
		aeHelper.setUp();
	}
	@AfterMethod(alwaysRun=true)
	public void afterMethod(){
		session.close();
		aeHelper.tearDown();
	}
	
	protected void setField(Object target, String fieldName, Object value){
		try {
			Field f = target.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			f.set(target, value);
		} catch (Throwable e) {
			throw new RuntimeException("Could not set field", e);
		}
	}
}
