package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class AdminService extends Controller {

    public static void welcome() {
          AppDao appdao = new AppDao();
	
          List<App> applist = appdao.getAllApps();          
          render(applist);
    }

    public static void editApp(App app){
          render(app);
    }

    public static void editAppSave(App app, String name, String information){
          AppDao appdao = new AppDao();
          app.name = name;
          app.information = information;
          appdao.save(app);   
          welcome();       
    }

    public static void deleteApp(App app){
          AppDao appdao = new AppDao();
          appdao.delete(app);
          welcome();
    }

    public static void addApp()
    {
          render();
    }

    public static void addAppSave(String id, String name, String information)
    {
          AppDao appdao = new AppDao();
          App app = new App(id, name, information);
          appdao.save(app);     
          welcome();
    }
}