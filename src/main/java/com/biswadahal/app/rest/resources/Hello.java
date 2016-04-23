package com.biswadahal.app.rest.resources;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;

import com.biswadahal.app.models.HelloEntity;
import com.biswadahal.app.models.HelloEntity.EnumType;
import com.biswadahal.app.services.HelloService;
import com.google.inject.servlet.RequestScoped;

@Path("")
@RequestScoped
public class Hello {
	@Inject
	private HelloService helloService;
	
	@Inject
	private HttpServletRequest httpRequest;
	
	@Context
	private Request request;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<HelloEntity> hello(){
		String msg = String.format("Injected \n\t model = helloModel  = %s, \n\t request = %s, \n\t httpRequest = %s, \n\t this =%s \n\t ofy = %s", helloService, request, httpRequest, this, ofy());
		HelloEntity page = new HelloEntity();
		page.setDummyField1("Field 1");
		page.setEnumType(EnumType.VAL1);
		ofy().save().entities(page).now();
		List<HelloEntity> pages  = ofy().load().type(HelloEntity.class).list();
		
		msg = String.format("%s \n\t\n Test output = \nadd(2,3) = %s, pages = %s", msg, helloService.add(2, 3), pages);
		System.out.println(msg);
		return pages;
	}
}
