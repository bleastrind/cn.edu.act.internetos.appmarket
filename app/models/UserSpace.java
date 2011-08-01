//package models;
import java.io.*;
import java.util.List;
import java.util.*;
/*
 * UserSapce is a class of UserSpace and for UserSpaceDao;
 */

public class UserSpace {
	//Fields
	
	public String id;
    public List<String> AppIds = new ArrayList<String>();

    //Constructors
    public UserSpace(){
    	
    }
    
	public UserSpace(String id){
		this.id = id;
        //AppIds = new ArrayList<String>();
	}
	
	public UserSpace(String id, List<String> AppIds){
		this.id = id;
		this.AppIds = AppIds;
	}
	
	//Property Accessors
	
	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
	
	public List<String> getAppIds(){
		return this.AppIds;
	}
	public void setAppIds(String appid){
		this.AppIds.add(appid);
	}
	public void setAppIds(List<String> list){
		this.AppIds = list;
	}
	
}
