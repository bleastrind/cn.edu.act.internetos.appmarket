package models;
/*
 * class for AppDao with some methods
 */


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
	Cluster cluster = getOrCreateCluster("Test Cluster", "127.0.0.1:9160");;
	Keyspace ko = createKeyspace(KEYSPACE, cluster);
	Mutator<String> m = createMutator(ko,se);
	String cf = "AppTestTable";
	static final Logger log = LoggerFactory.getLogger(AppDao.class);
	public void save(App instance){
		log.debug("saving App instance");
		try{
			
			//cluster = getOrCreateCluster("Test Cluster", "127.0.0.1:9160");
		    //ko = createKeyspace(KEYSPACE, cluster);
			//Mutator<String> m = createMutator(ko,se);
		    //HFactory t = new HFactory();
		    //t.createColumnFamilyDefinition("Key","testTable2");
		  MutationResult mr = m.insert(instance.getId(), cf, createColumn(instance.getName(),instance.getInformation(),se,se));
		  log.debug("insert execution time: {}", mr.getExecutionTimeMicro());
		  MutationResult mr2 = m.insert(instance.getId(), cf, createColumn("column_name",instance.getName(),se,se));
		  log.debug("insert execution time: {}", mr.getExecutionTimeMicro());
		 // MutationResult mr3 = m.insert(instance.getId(), cf, createColumn("column_information",instance.getInformation(),se,se));
		  //log.debug("insert execution time: {}", mr.getExecutionTimeMicro());
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
				        instance.getName(), se);
			 ColumnQuery<String, String, String> q2 = createColumnQuery(ko, se, se, se);
			    q2.setName(instance.getName()).setColumnFamily(cf);
			    QueryResult<HColumn<String, String>> r2 = q2.setKey(instance.getId())
			        .execute();
			    assertNotNull(r2);
			    System.out.println(r2);
			    assertNull("Value should have been deleted", r2.get());
			
		 }catch(RuntimeException re){
			 log.error("deleting failed",re);
				throw re;
		 }
	}
	
	public String getAllApps(){
		//System.out.println("nihao!");有待完善！
		return "nihao";		
	}
	
	public App findById(String id){
		log.debug("getting App instance with id:"+id);
		try{
			//String cf = "AppTestTable";
			App instance = new App();
			instance.setId(id);
			//instance.setName(name);
			ColumnQuery<String, String, String> q = createColumnQuery(ko, se, se, se);
		    q.setName("column_name");
		    q.setColumnFamily(cf);
		    QueryResult<HColumn<String, String>> r = q.setKey(instance.getId())
		        .execute();
		    assertNotNull(r);

		    HColumn<String, String> c = r.get();
		    assertNotNull(c);
		    instance.setInformation(c.getValue());
		    
		    //第二次查询
		    //ColumnQuery<String, String, String> q2 = createColumnQuery(ko, se, se, se);
		    q.setName(instance.getInformation());
		    QueryResult<HColumn<String, String>> r2 = q.setKey(instance.getId())
	        .execute();
	    assertNotNull(r2);

	    HColumn<String, String> c2 = r2.get();
	    assertNotNull(c2);
	    System.out.println(c2);
	    System.out.println("Key:"+id);
	    instance.setInformation(c2.getValue());
	    instance.setName(c2.getName());
		    //String name = c.getName();
		    //assertEquals(instance.getName(), name);
		    //System.out.println("Key_name:"+name);
		    System.out.println("App_name:"+instance.getName());
		    System.out.println("App_information:"+instance.getInformation());
		    //System.out.println(r.get());
		    assertEquals(q, r.getQuery());
		    return instance;
		}catch(RuntimeException re){
			log.error("get failed",re);
			throw re;
		}

	}
}
