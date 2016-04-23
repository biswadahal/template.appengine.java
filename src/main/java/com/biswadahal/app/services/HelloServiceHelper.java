package com.biswadahal.app.services;

import com.google.inject.Singleton;

@Singleton
public class HelloServiceHelper {
	public int add(int a , int b){
		return a + b;
	}
	
	public int mult(int a, int b){
		return a*b;
	}
}
