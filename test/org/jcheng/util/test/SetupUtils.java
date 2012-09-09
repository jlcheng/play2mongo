package org.jcheng.util.test;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractRefreshableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Provides a global variable for looking up the Spring Application context for unit tests.
 * 
 * 
 * @author jcheng
 *
 */
public final class SetupUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(SetupUtils.class);
	
	private static AbstractRefreshableApplicationContext appContext = null;
	
	private SetupUtils() { }
	
	public static ApplicationContext getApplicationContext() {
		return appContext;
	}
	
	public static void setup() {
		if ( appContext == null ) {
			appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
			logger.debug("appContext started");
		}
	}
	
	public static void teardown() {
		if ( appContext != null ) {
			appContext.stop();
			appContext.destroy();
			appContext = null;
			logger.debug("appContext stopped");
		}
	}

}
