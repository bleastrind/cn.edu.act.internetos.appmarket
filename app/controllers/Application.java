package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }

    public static void login() {
        render();
    }

    public static void getAccount(String account, String password) {
     //   if (account.equals("admin") && password.equals("admin"))

            AdminService.welcome();
        //    return;
        
       // UserDao userdao = new UserDao();
       // User user = userdao.findByAccount(account);
       // UserService.welcome(user);
    }
}