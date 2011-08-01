import java.io.*;
import java.util.List;
/*
 * UserSapce is a class of UserSpace and for UserSpaceDao;
 */
public class UserSpace {
	//Fields
	
	private String id;
	private List<String> appidList = null;
	//private String appid;
	
	//Constructors
	public UserSpace(){
		
	}
	public UserSpace(String id){
		this.id = id;
	}
	
	//Property Accessors
	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
	
	public String getAppidList(String i){
		int j = Integer.parseInt(i);
		return appidList.get(j);
	}
	//public String getAppid(){
		//return this.appid;
	//}
	/*public void setAppId(int i, String appid){
		this.id = appidList.get(i);
		this.appidList.get(i) = ?;
	}*/
	public void setAppidList(int i, String appid){
		this.appidList.add(i, appid);
	}
	/*public void setAppid(String appid){
		this.appid=appid;
	}*/
	
}
