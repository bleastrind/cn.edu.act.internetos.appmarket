package models;
import java.util.List;
import java.util.ArrayList;

public class Tags{
	public String id;
	public List<String> tags;
	
	public Tags(){
	}
	
	public Tags(String id){
	this.id = id;
	}
	
	public String getId(){
	return this.id;
	}

	public void setId(String id){
	this.id = id;
	}
	
	public Tags(String id,List<String> tags){
	this.id = id;
	this.tags = tags; 
	}
	
	public List<String> getTags(){
		return this.tags;
	}
	
	public void setTags(List<String> tags){
		this.tags=tags;
	}
	
	public void addTags(String tag){
		this.tags.add(tag);
	}
	
}