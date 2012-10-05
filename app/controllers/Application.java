package controllers;

import java.util.LinkedHashMap;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Application extends Controller {

	public static Result index() {
		Model model = new Model();
		return ok(login.render(model));
	}
	
	public static Result login() {
		Model model = new Model();
		User user = new User();
		return ok(main.render(model, user));
	}

	public static class Model {
		
		public String title = "A Playframework + Bootstrap Template";
		
		public String sidebarTitle1 = "Sidebar 1";
		public final LinkedHashMap<String, String> sidebar1 = new LinkedHashMap<String, String>();
		
		public String sidebarTitle2 = "Sidebar 2";
		public final LinkedHashMap<String, String> sidebar2 = new LinkedHashMap<String, String>();
		
		public String company = "Your Company";
		
		public Model() {
			this.sidebar1.put("Google", "http://www.google.com");
			this.sidebar1.put("Bing", "http://www.bing.com");
			this.sidebar1.put("Yahoo", "http://www.yahoo.com");
			
			this.sidebar2.put("Facebook", "http://www.facebook.com");
			this.sidebar2.put("Twitter", "http://www.bing.com");
			this.sidebar2.put("G", "https://plus.google.com");
		}
	}
	
	public static class User {
		
		public String username;
		public String password;
	}

}
