import global.GlobalContext;
import play.Application;
import play.GlobalSettings;

public class Global extends GlobalSettings {

	@Override
	public void onStart(Application app) {
		GlobalContext.getApplicationContext();
	}

	@Override
	public void onStop(Application app) {
		GlobalContext.stopApplicationContext();
	}
	
}
