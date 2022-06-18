package com.derrick.covington;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ComponentScan("com.derrick.covington")
@EnableCaching
public class IPAddressManagerApp {
	
	static ApplicationContext context = null;
	
	static synchronized void setContext(ApplicationContext theContext) {
		context = theContext;
	}
	
	public static void main(String[] args) {
		context = SpringApplication.run(IPAddressManagerApp.class, args);
		IPAddressManagerApp.setContext(context);
		
	}
}
