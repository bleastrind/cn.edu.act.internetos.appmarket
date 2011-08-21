package cn.edu.act.internetos.appmarket.service;

import java.util.*;
import models.*;

public class RegistService{

	public static void regist(String id, String account, String password, String name){
		User user = new User(id, account, password, name);	
		UserDao userdao = new UserDao();	
		userdao.save(user);

		UserSpace userspace = new UserSpace(id);
		UserSpaceDao userspacedao = new UserSpaceDao();
		userspacedao.save(userspace);
	}
   
}