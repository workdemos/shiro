package com.max.kerrer;

public class Spring3HelloWorld {
	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public void sayHello() {
		System.out.println(" Hello World: Spring 3 in Action, This is bean with name " + name);
	}
}
