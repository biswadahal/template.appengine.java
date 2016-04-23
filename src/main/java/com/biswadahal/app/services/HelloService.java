package com.biswadahal.app.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class HelloService {
	@Inject
	private HelloServiceHelper helper;
	
	public int add(int a , int b){
		return helper.add(a, b);
	}
	
	public int mult(int a, int b){
		return helper.mult(a, b);
	}

}
