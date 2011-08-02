package models;
/*
 * class for AppDao with some methods
 */

import java.util.*;

import static me.prettyprint.hector.api.factory.HFactory.createColumn;
import static me.prettyprint.hector.api.factory.HFactory.createColumnQuery;
import static me.prettyprint.hector.api.factory.HFactory.createKeyspace;
import static me.prettyprint.hector.api.factory.HFactory.createMutator;
import static me.prettyprint.hector.api.factory.HFactory.getOrCreateCluster;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.mutation.MutationResult;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.ColumnQuery;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.cassandra.serializers.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppDao {
	final static String KEYSPACE = "Keyspace1";
	static final StringSerializer se = new StringSerializer();
	static final Logger log = LoggerFactory.getLogger(AppDao.class);
	
	final List<String> AppIds = new ArrayList<String>();
	
	Cluster cluster = getOrCreateCluster("Test Cluster", "127.0.0.1:9160");;
	Keyspace ko = createKeyspace(KEYSPACE, cluster);
	String cf = "AppTestTable";
	
	Mutator<String> m = createMutator(ko,se);
	
	public void save(App instance){
		log.debug("saving App instance");
		try{
			
			MutationResult mr = m.insert(instance.getId(), cf, createColumn("column_name",instance.getName(),se,se));
			log.debug("insert execution time: {}", mr.getExecutionTimeMicro());
			MutationResult mr2 = m.insert(instance.getId(), cf, createColumn("column_info",instance.getInformation(),se,se));
			log.debug("insert execution time: {}", mr2.getExecutionTimeMicro());
			MutationResult mr3 = m.insert(instance.getName(), cf, createColumn("name_info",instance.getInformation(),se,se));
			log.debug("insert execution time: {}", mr3.getExecutionTimeMicro());

			AppIds.add(instance.getId());
			MutationResult mr4 = m.insert("allAppIds", cf, createColumn("AppIds",listToString(AppIds),se,se));
			log.debug("insert execution time: {}", mr4.getExecutionTimeMicro());
			log.debug("save successful");
		}catch(RuntimeException re){
			log.error("save failed",re);
			throw re;
		}
	}
	

	public void delete(App instance){
		log.debug("deleting App instance"); 
		try{
			MutationResult mr = m.delete(instance.getId(), cf,
					"column_name", se);
			MutationResult mr2 = m.delete(instance.getId(), cf,
					"column_info", se);
			MutationResult mr3 = m.delete(instance.getId(), cf,
					"name_info", se);
					
			ColumnQuery<String, String, String> q2 = createColumnQuery(ko, se, se, se);
			q2.setName(instance.getName()).setColumnFamily(cf);
			QueryResult<HColumn<String, String>> r2 = q2.setKey(instance.getId())
			    .execute();
			assertNotNull(r2);
			assertNull("Value should have been deleted", r2.get());
			
		}catch(RuntimeException re){
			log.error("deleting failed",re);
				throw re;
		}
	}
	
	public List<App> getAllApp(){
		List<App> app_list = new ArrayList<App>();
		for(String i:getAllAppIds())
			app_list.add(findById(i));
		return app_list;
	}
	private List<String> getAllAppIds(){
		List<String> list = new ArrayList<String>();

		ColumnQuery<String, String, String> q = createColumnQuery(ko, se, se, se);
	    q.setName("AppIds");
	    q.setColumnFamily(cf);
	    QueryResult<HColumn<String, String>> r = q.setKey("allAppIds")
	        .execute();
	    assertNotNull(r);
	    HColumn<String, String> c = r.get();
	    assertNotNull(c);
	    list = stringToList(c.getValue());
		
		return list ;		
	}
	
	public App findById(String id){
		log.debug("getting App instance with id:"+id);
		try{
			//The first Query,the name
			ColumnQuery<String, String, String> q = createColumnQuery(ko, se, se, se);
		    q.setName("column_name");
		    q.setColumnFamily(cf);
		    QueryResult<HColumn<String, String>> r = q.setKey(id)
		        .execute();
		    assertNotNull(r);
		    HColumn<String, String> c = r.get();
		    assertNotNull(c);
		    
		    //the second Query,the information
		    q.setName("column_info");
		    QueryResult<HColumn<String, String>> r2 = q.setKey(id)
	        .execute();
			assertNotNull(r2);
			HColumn<String, String> c2 = r2.get();
			assertNotNull(c2);
		
			//put the parameters to the App class
			App instance = new App();
			instance.setId(id);
			instance.setName(c.getValue());
			instance.setInformation(c2.getValue());

			return instance;
		}catch(RuntimeException re){
			log.error("get failed",re);
			throw re;
		}
	}
	
	String listToString(List<String> list){
		String str = "";
		for(String i:list){
			str += i + ",";
		}
		return str;
	}

	List<String> stringToList(String str){
		List<String> list = new ArrayList<String>();
		for(String i:str.split(",")){
			list.add(i);
		}
		return list;
	}
}

