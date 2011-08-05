package controllers;

import play.*;
import play.mvc.*;
import play.cache.*;

import java.util.*;

import models.*;

public class AppService extends Controller {

	@Before(only={"listAllApp","addAppToUser","deleteAppFormUser"})
	public static void checkUser(){
		User user = Cache.get(session.getId() + "-users", User.class);
		System.out.println(user);
		if(user == null)
			Application.index();
	}

    public static void index() {
        render();
    }
	public static void listAllApps() throws Exception{
		App a= new App("0","renern","sdfasdfasd");

		AppDao appdao = new AppDao();
		appdao.save(a);
		List<App> applist = appdao.getAllApps();
		System.out.println(applist.size());
		render(applist);
	}
	
	public static void listAllApp() throws Exception{
		User user = Cache.get(session.getId() + "-users", User.class);
		UserSpaceDao userspacedao = new UserSpaceDao();
		AppDao appdao = new AppDao();
	    boolean flag; 
		UserSpace userspace = userspacedao.getUserSpace(user);

 		List<App> applist = new ArrayList<App>();
		for (String id:userspace.AppIds)
		{
			if(id == null || id.isEmpty() || appdao.findById(id) == null)
				continue;
			flag = false;
			for (App app: applist)
				if ((app.id.equals(id)) || (app.name.equals(appdao.findById(id).name)))
				{
					flag = true;
				}
				if (flag)
					continue;
			applist.add(appdao.findById(id));
		}

		System.out.println(applist.size());
		render(applist);
	}
	
	
	public static void addUserApp()
	{
		User user = Cache.get(session.getId() + "-users", User.class);
	
		AppDao appdao = new AppDao();	      

		List<App> applist = appdao.getAllApps();
		render(applist);
	}
	
	public static void addUserAppSave(App app)
	{
		User user = Cache.get(session.getId() + "-users", User.class);
		UserSpaceDao userspacedao = new UserSpaceDao();	
		UserSpace userspace = userspacedao.getUserSpace(user);
		
		for (String id:userspace.AppIds)
		{
			if (id.equals(app.id))
			{
				render("AppService/addfail.html");
				return;
			}
		}
		userspace.AppIds.add(app.id);
		userspacedao.save(userspace);
		render("AppService/addsuccess.html");
		
	}
	
	public static void deleteUserApp(App app)
	{
		User user = Cache.get(session.getId() + "-users", User.class);
		UserSpaceDao userspacedao = new UserSpaceDao();	
		UserSpace userspace = userspacedao.getUserSpace(user);
		List<String> temp = userspace.AppIds;
	
		for (int i = 0; i < userspace.AppIds.size(); i++)
		{
			if (userspace.AppIds.get(i).equals(app.id))
			{
				temp.remove(userspace.AppIds.get(i));
			}
		}
	/*	for (String id:userspace.AppIds)
		{
			if (id.equals(app.id))
			{
				temp.remove(id);
			}
		}*/
		userspace.AppIds = temp;
		userspacedao.save(userspace);
		render("AppService/deletesuccess.html");
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