package models;
import java.io.*;
import java.util.List;
import java.util.*;
/*
 * UserSapce is a class of UserSpace and for UserSpaceDao;
 */

public class UserSpace {
	//Fields
	
	private String id;
      public List<String> AppIds;

	public UserSpace(String id){
		this.id = id;
            AppIds = new ArrayList<String>();
	}
	
	//Property Accessors
	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
	

	
}
