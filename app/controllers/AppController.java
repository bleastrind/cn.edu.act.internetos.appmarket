package controllers;

import cn.edu.act.internetos.appmarket.service.*;
import play.*;
import play.mvc.*;
import play.cache.*;
import play.mvc.Http.*;
import java.util.*;
import models.*;

public class AppController extends Controller {

	@Before(only={"listAllApp","addAppToUser","deleteAppFormUser"})
	public static void checkUser(){
		User user = Cache.get(session.getId() + "-users", User.class);
		if(user == null)
			Application.index();
	}

    public static void index() {
        render("AppService/index.html");
    }
	
	public static void listAllApps(){
		List<App> applist = AppService.getAllApps();
        render("AppService/listAllApps.html", applist);
	}
	
	public static void listAllApp(){
		User user = Cache.get(session.getId() + "-users", User.class);
		List<App> applist = AppService.getAllApp(user);
		List<AppConfig> configlist = AppService.getAllConfig(user);
		render("AppService/listAllApp.html", applist, configlist, user);
	}
	
	public static void addUserApp()
	{
		User user = Cache.get(session.getId() + "-users", User.class);
		List<App> applist = AppService.getAllApps();
		render("AppService/addUserApp.html", applist);
	}
	
	public static void addUserAppSave(App app)
	{
		User user = Cache.get(session.getId() + "-users", User.class);
		if (AppService.addUserApp(app, user))
			render("AppService/addsuccess.html");	
		else
			render("AppService/addfail.html");		
	}
	
	public static void deleteUserApp(App app)
	{
		User user = Cache.get(session.getId() + "-users", User.class);
		AppService.deleteUserApp(app, user);
		render("AppService/deletesuccess.html");
	}
	
	public static void listApp(App app){		
		User user = Cache.get(session.getId() + "-users", User.class);
		render ("AppService/listApp.html", app, user);
	}
	
	public static void installConfig(String userId, String appId, String config){
		AppService.setConfig(userId, appId, config);
		render("AppService/SetConfigSuccess.html");
	}

}