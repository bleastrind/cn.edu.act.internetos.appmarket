package cn.edu.act.internetos.appmarket.service;

import java.util.*;
import models.*;

public class AdminService{



    public static void saveApp(App app, String name, String information){
          AppDao appdao = new AppDao();
          app.name = name;
          app.information = information;
          appdao.save(app);        
    }

    public static void deleteApp(App app){
        AppDao appdao = new AppDao();
        appdao.delete(app);
    }

    public static void addAppSave(String id, String name, String information)
    {
          AppDao appdao = new AppDao();
          App app = new App(id, name, information);
          appdao.save(app);     
    }
}