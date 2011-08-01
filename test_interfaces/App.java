//import me.prettyprint.hector.api.ApiV2SystemTest;
/*
 * App is a class of the Application and for AppDao;
 */
public class App {
	//Fields
	
	private String id;
	private String name;
	private String information;
	
	//Construcors
	public App(){
		
	}
	
	public App(String id, String name, String information){
		this.id = id;
		this.name = name;
		this.information = information;
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
}
