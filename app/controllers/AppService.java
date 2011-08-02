package controllers;

import play.*;
import play.mvc.*;
import play.cache.*;

import java.util.*;

import models.*;

public class AppService extends Controller {

	@Before(only={"addAppToUser","deleteAppFormUser"})
	public static void checkUser(){
		User user = Cache.get(session.getId() + "-users", User.class);
		if(user == null)
			Application.index();
	}

    public static void index() {
        render();
    }
	
	public static void listAllApp(){
		App a= new App("0","renern","sdfasdfasd");
		
		AppDao appdao = new AppDao();
		appdao.save(a);
		List<App> applist = appdao.getAllApp();
		System.out.println(applist.size());
		render(applist);
	}
	
	public static void listApp(App app){
		
		render (app);
	}
	public static void addAppToUser(App app){
		User user = Cache.get(session.getId() + "-users", User.class);
		
		UserSpaceDao ud = new UserSpaceDao();
		UserSpace userspace = ud.getUserSpace(user);
		List<String> appidList = userspace.getAppIds();
		appidList.add(app.getId());
		userspace.setAppIds(appidList);
		ud.save(userspace);
		if (true)
		render("AppService/success.html");
		else render ("AppService/failed.html");
	}
	
	public static void deleteAppFromUser(App app){
		User user = Cache.get(session.getId() + "-users", User.class);
	
		UserSpaceDao ud = new UserSpaceDao();
		UserSpace userspace = ud.getUserSpace(user);
		List<String> appidList = userspace.getAppIds();
		appidList.remove(app.getId());
		userspace.setAppIds(appidList);
		ud.save(userspace);
		if (true)
		render("AppService/success.html");
		else render ("AppService/failed.html");
	}
}