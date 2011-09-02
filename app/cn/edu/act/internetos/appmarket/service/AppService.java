package cn.edu.act.internetos.appmarket.service;

import java.util.*;
import models.*;

public class AppService{
	
	public static List<App> getAllApp(User user){		
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
		return applist;
	}
	
	
    public static List<App> getAllApps()  
	{
        AppDao appdao = new AppDao();	
        List<App> applist = appdao.getAllApps();     
        return applist;		  
    }
	
	public static boolean addUserApp(App app, User user)
	{
		UserSpaceDao userspacedao = new UserSpaceDao();	
		UserSpace userspace = userspacedao.getUserSpace(user);
		
		for (String id:userspace.AppIds)
			if (id.equals(app.id))
				return false;
		userspace.AppIds.add(app.id);
		userspacedao.save(userspace);
		return true;		
	}
	
	public static void deleteUserApp(App app, User user)
	{
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
		userspace.AppIds = temp;
		userspacedao.save(userspace);
	}
	
	public static List<App> listAppByRank(){
		AppDao appdao = new AppDao();
		List<App> apps = appdao.getAllApps();
		List<App> appList = new ArrayList<App>();
		int j = 0;
		for (App i:apps)
		{
			j++;
			for (App app:apps)
			{
				if (Integer.parseInt(app.rank)==j) appList.add(app);
			}
		}
		
		return appList;
	}
	
	
	public static List<String> listAppByUser(User user) throws Exception{
		FavorDao favordao = new FavorDao();
		Favor favor = favordao.getFavor(user);
		List<String> temp = favor.Address;
		
		return temp;
	}
	
	public static List<App> listAppByTags(Tags tags){
	
		AppDao appdao = new AppDao();
		TagsDao tagsdao = new TagsDao();
		List<App> apps = appdao.getAllApps();
		List<App> appList = new ArrayList<App>();
		for (App app:apps)
		{
			Tags app_tags = tagsdao.getTags(app);
			boolean out_flag = true;
			for (String i:tags.tags)
			{
				boolean flag = false;
				for (String j:app_tags.tags)
				{
					if (i==j) {flag = true;break;}
				}
				if (flag==true) continue; else out_flag = false; 
			}
			
			if (out_flag == true) appList.add(app);
		}
		
		return appList;
	}
	
}