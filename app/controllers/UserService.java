package controllers;

import play.*;
import play.mvc.*;
import play.cache.*;
import java.util.*;

import models.*;

public class UserService extends Controller {

    public static void welcome() {
		User user = Cache.get(session.getId() + "-users", User.class);
        render(user);
    }
	
}