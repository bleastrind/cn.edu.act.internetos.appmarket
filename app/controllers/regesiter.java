package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class regesiter extends Controller {

    public static void index() {
        render();
    }
	
	public static void regesiter(String id, String account, String password, String name){
		//User user = new User("sd","sf","sda","asdasdas");	
		User user = new User(id, account, password, name);	
		//System.out.println(account);
		UserDao userdao = new UserDao();	
		userdao.save(user);

		UserSpace userspace = new UserSpace(id);
		UserSpaceDao userspacedao = new UserSpaceDao();
		userspacedao.save(userspace);
		if (true) render("regesiter/success.html");
		else render("failed.html");
	}
   
}