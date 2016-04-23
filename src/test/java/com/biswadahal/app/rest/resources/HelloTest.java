package com.biswadahal.app.rest.resources;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.List;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.biswadahal.AppEngineTests;
import com.biswadahal.app.models.HelloEntity;
import com.biswadahal.app.rest.resources.Hello;
import com.biswadahal.app.services.HelloService;

public class HelloTest extends AppEngineTests{
	@Mock
	private HelloService service;
	
	@BeforeMethod
	public void init(){
		MockitoAnnotations.initMocks(this);
		doReturn(1).when(service).add(anyInt(), anyInt());
	}
	
	@Test
	public void dummyTest(){
		Hello resource = new Hello();
		setField(resource, "helloService", service);
		List<HelloEntity> entites = resource.hello();
		assertNotNull(entites);
		assertEquals(entites.size(),1);
	}

}
