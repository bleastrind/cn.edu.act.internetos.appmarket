package controllers;

import cn.edu.act.internetos.appmarket.service.*;
import play.*;
import play.mvc.*;
import java.util.*;
import models.*;

public class AdminController extends Controller {

    public static void welcome() {   
		List<App> applist = AppService.getAllApps();
        render("AdminService/welcome.html", applist);
    }

    public static void editApp(App app){
        render("AdminService/editApp.html", app);
    }

    public static void editAppSave(App app, String name, String information){
		AdminService.saveApp(app, name, information);
        welcome();       
    }

    public static void deleteApp(App app){
        AdminService.deleteApp(app);
        welcome();
    }

    public static void addApp()
    {
        render("AdminService/addApp.html");
    }

    public static void addAppSave(String id, String name, String information)
    {
		AdminService.addAppSave(id, name, information);
        welcome();
    }
}