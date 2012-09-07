package org.jcheng.util.test;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractRefreshableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class SetupUtils {
	
	private static AbstractRefreshableApplicationContext appContext = null;
	
	private SetupUtils() {
		
	}
	
	public static ApplicationContext getApplicationContext() {
		return appContext;
	}
	
	public static void setup() {
		if ( appContext == null ) {
			appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
			System.out.println("appContext started");			
		}
	}
	
	public static void teardown() {
		if ( appContext != null ) {
			appContext.stop();
			appContext = null;
			System.out.println("appContext stopped");
		}
	}

}
