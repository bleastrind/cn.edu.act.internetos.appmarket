package models;
/*
 * User is a class of the User and for UserDao;
 */

public class User {
	//Fields
	
	public String id;
	public String account;
	public String password;
	public String name;

	
	//Constructors
	public User(){
		
	}
	public User(String id, String account){
		this.id = id;
		this.account = account;		
	}
	public User(String id, String account, String password){
		this.id = id;
		this.account = account;
		this.password = password;
	}
	
	public User(String id, String account, String password, String name){
		this.id = id;
		this.account = account;
		this.password = password;
		this.name = name;
	}
	
	//Property Accessors
	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
	
	public String getAccount(){
		return this.account;
	}
	public void setAccount(String account){
		this.account = account;
	}
	
	public String getPassword(){
		return this.password;
	}
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	
}
