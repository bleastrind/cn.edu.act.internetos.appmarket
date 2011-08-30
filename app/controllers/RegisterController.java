package controllers;

import cn.edu.act.internetos.appmarket.service.*;
import play.*;
import play.mvc.*;
import java.util.*;
import models.*;

public class RegisterController extends Controller {

    public static void index() {
        render("Register/index.html");
    }
	
	public static void regist(String id, String account, String password, String name){
		RegistService.regist(id, account, password, name);
		if (true) render("Register/success.html");
		else render("Register/success.html");
	}
   
}