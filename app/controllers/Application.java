package controllers;

import java.util.LinkedHashMap;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

	public static Result index() {
		Model model = new Model();
		model.title = "A Playframework + Bootstrap Template";
		
		model.sidebarTitle1 = "side bar #1";
		model.sidebar1.put("Google", "http://www.google.com");
		model.sidebar1.put("Bing", "http://www.bing.com");
		model.sidebar1.put("Yahoo", "http://www.yahoo.com");
		
		model.sidebarTitle2 = "side bar #2";
		model.sidebar2.put("Facebook", "http://www.facebook.com");
		model.sidebar2.put("Twitter", "http://www.bing.com");
		model.sidebar2.put("G", "https://plus.google.com");
		
		model.company = "Huge";
		
		return ok(index.render(model));
	}

	public static class Model {
		
		public String title = "";
		
		public String sidebarTitle1 = "Sidebar 1";
		public final LinkedHashMap<String, String> sidebar1 = new LinkedHashMap<String, String>();
		
		public String sidebarTitle2 = "Sidebar 2";
		public final LinkedHashMap<String, String> sidebar2 = new LinkedHashMap<String, String>();
		
		public String company = "";
	}

}
