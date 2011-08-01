import java.util.List;
import java.util.ArrayList;
/*
 * App is a class of the Application and for AppDao;
 */

//package models;

public class App {

	//Fields
	
	public String id;
	public String name;
	public String information;
	
	public List<String> AppIds = new ArrayList<String>();
	
	//Construcors
	
	public App(){
		
	}
	
	public App(String id, String name, String information){
		this.id = id;
		this.name = name;
		this.information = information;
	}
	
	public App(String id, String name, String information, List<String> AppIds){
		this.id = id;
		this.name = name;
		this.information = information;
		this.AppIds = AppIds;
	}
	
	//Property accessors
	
	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
	
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	public String getInformation(){
		return this.information;
	}
	public void setInformation(String information){
		this.information = information;
	}
	
	public List<String> getAppIds(){
		return this.AppIds;
	}
	public void setAppIds(){
		this.AppIds.add(this.id);
	}
}
