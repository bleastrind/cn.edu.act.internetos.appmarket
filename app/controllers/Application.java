package controllers;


import play.*;
import play.mvc.*;
import play.cache.*;
import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }

    public static void show(String account, String password)
    {
        UserDao userdao = new UserDao();
        AppDao appdao = new AppDao();
        User user = userdao.findByAccount(account);

		//User user = Cache.get(session.getId() + "-users", User.class);
		//if(user == null) {
			// Cache miss
			Cache.set(session.getId() + "-users", user, "30mn");
		//}

		
		//	  UserSpace space = userdao.getUserSpace(user);
        UserSpace space = new UserSpace(user.id);

        List<App> applist = new ArrayList<App>();
             
//test
//userspace.AppIds.add("1");
//userspace.AppIds.add("2");
//userspace.AppIds.add("3");

        for (int i = 0; i < space.AppIds.size(); i++)
            applist.add(appdao.findById(space.AppIds.get(i)));


        render(user, applist);
    }

    public static void AddApp(List<App> nowapp)
    {
        AppDao appdao = new AppDao();
        List<App> toaddapplist = new ArrayList<App>(); 
        List<App> allapp = appdao.getAllApp();
        
        for (int i = 0; i < allapp.size(); i++)
        {
            boolean flag = true;
            for (int j = 0; j < nowapp.size(); j++)
                if (allapp.get(i).id == nowapp.get(j).id)
                    flag = false;
            if (flag)
                toaddapplist.add(allapp.get(i));
        }
        render(toaddapplist);
           
    }
	
}