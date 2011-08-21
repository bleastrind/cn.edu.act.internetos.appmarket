package controllers;

import cn.edu.act.internetos.appmarket.service.*;
import play.*;
import play.mvc.*;
import play.cache.*;
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
        render();
    }
	
	public static void listAllApp(){
		User user = Cache.get(session.getId() + "-users", User.class);
		render(AppService.getAllApp(user));
	}
	
	
	public static void addUserApp()
	{
		User user = Cache.get(session.getId() + "-users", User.class);
		render(AppService.getAllApps());
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
		render (app);
	}
	
}