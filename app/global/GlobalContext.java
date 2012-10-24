/**
 * 
 */
package global;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import play.Logger;

/**
 * @author jcheng
 *
 */
public final class GlobalContext {

	private static final Object appContextLock = new Object();
	private static ClassPathXmlApplicationContext appContext;
	
	private GlobalContext() {}
	
	public static ApplicationContext getApplicationContext() {
		synchronized (appContextLock) {
			try {
				if ( GlobalContext.appContext == null ) {
					GlobalContext.appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
					Logger.debug("Spring applicationContext started");
				}
			} catch ( Exception e ) {
				Logger.error(e.getMessage());
			}		
		}
		return appContext;
	}
	
	public static void stopApplicationContext() {
		synchronized (appContextLock) {
			try {
				GlobalContext.appContext.stop();
				GlobalContext.appContext = null;
				Logger.debug("Spring applicationContext stopped");
			} catch ( Exception e ) {
				Logger.error(e.getMessage());
			}
		}	
	}
}
