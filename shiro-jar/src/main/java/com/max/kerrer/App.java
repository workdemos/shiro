package com.max.kerrer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/beanconfig.xml");
		Spring3HelloWorld bean = (Spring3HelloWorld) context.getBean("firstBean");
		bean.sayHello();
	}
}
