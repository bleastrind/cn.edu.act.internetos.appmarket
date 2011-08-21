package controllers;

import cn.edu.act.internetos.appmarket.service.*;
import play.*;
import play.mvc.*;
import java.util.*;
import models.*;

public class RegisterController extends Controller {

    public static void index() {
        render();
    }
	
	public static void regist(String id, String account, String password, String name){
		RegistService.regist(id, account, password, name);
		if (true) render("regesiter/success.html");
		else render("failed.html");
	}
   
}