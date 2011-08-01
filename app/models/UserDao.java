package models;

import static me.prettyprint.hector.api.factory.HFactory.createColumn;
import static me.prettyprint.hector.api.factory.HFactory.createColumnQuery;
import static me.prettyprint.hector.api.factory.HFactory.createKeyspace;
import static me.prettyprint.hector.api.factory.HFactory.createMutator;
import static me.prettyprint.hector.api.factory.HFactory.getOrCreateCluster;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 *一些常用的方法 
 */
public class UserDao {
	final static String KEYSPACE = "Keyspace1";
	static final StringSerializer se = new StringSerializer();
	Cluster cluster = getOrCreateCluster("Test Cluster", "127.0.0.1:9160");
	Keyspace ko = createKeyspace(KEYSPACE, cluster);
	Mutator<String> m = createMutator(ko,se);
	String cf = "UserTestTable";
	static final Logger log = LoggerFactory.getLogger(UserDao.class);
	
	public void save(User instance){
		log.debug("saving User instance");
		try{
			  MutationResult mr = m.insert(instance.getId(), cf, createColumn("column_name",instance.getName(),se,se));
			  log.debug("insert execution time: {}", mr.getExecutionTimeMicro());
			  MutationResult mr2 = m.insert(instance.getId(), cf, createColumn("column_account",instance.getAccount(),se,se));
			  log.debug("insert execution time: {}", mr2.getExecutionTimeMicro());
			  MutationResult mr3 = m.insert(instance.getId(), cf, createColumn("column_password",instance.getPassword(),se,se));
			  log.debug("insert execution time: {}", mr3.getExecutionTimeMicro());
			  MutationResult mr4 = m.insert(instance.getId(), cf, createColumn("column_information",instance.getInformation(),se,se));
			  log.debug("insert execution time: {}", mr4.getExecutionTimeMicro());
			  MutationResult mr5 = m.insert(instance.getAccount(), cf, createColumn("column_id",instance.getId(),se,se));
			  log.debug("insert execution time: {}", mr4.getExecutionTimeMicro());
			  
			  log.debug("save successful");
		}catch(RuntimeException re){
			log.debug("save failed");
			throw re;
		}
	}
	
	public void delete(User instance){
		log.debug("deleting User instance"); 
		try{
			 MutationResult mr = m.delete(instance.getAccount(), cf,
					 "column_id", se);
			
			 MutationResult mr2 = m.delete(instance.getId(), cf,
						 "column_name", se);
				    
			 MutationResult mr3 = m.delete(instance.getId(), cf,
							 "column_account", se);
					 
			 MutationResult mr4 = m.delete(instance.getId(), cf,
								 "column_password", se);
			 MutationResult mr5 = m.delete(instance.getId(), cf,
					 "column_information", se);
			ColumnQuery<String, String, String> q2 = createColumnQuery(ko, se, se, se);
			q2.setName("column_name").setColumnFamily(cf);
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
	
public User findByAccount(String account){
		log.debug("find a user");
		try{
			//At first,searching the id through the account
			User instance = new User();
			//instance.setId("1");
			
			ColumnQuery<String, String, String> q = createColumnQuery(ko, se, se, se);
		    q.setName("column_id");
		    q.setColumnFamily(cf);
		    QueryResult<HColumn<String, String>> r = q.setKey(account)
		        .execute();
		    assertNotNull(r);
		    instance.setAccount(account);//set accout;
		    HColumn<String, String> c = r.get();
		    assertNotNull(c);
		    instance.setId(c.getValue());//get the id!
		    
		    
		   //searching other parameters through the id
		    
		    //searching name!
		    q.setName("column_name");
		    q.setColumnFamily(cf);
		    QueryResult<HColumn<String, String>> r2 = q.setKey(instance.getId())
		        .execute();
		    assertNotNull(r2);
		    HColumn<String, String> c2 = r2.get();
		    assertNotNull(c2);
		    instance.setName(c2.getValue());//get the Name!
		   
		    //searching password!
		    q.setName("column_password");
		    q.setColumnFamily(cf);
		    QueryResult<HColumn<String, String>> r3 = q.setKey(instance.getId())
		        .execute();
		    assertNotNull(r3);
		    HColumn<String, String> c3 = r3.get();
		    assertNotNull(c3);
		    instance.setPassword(c3.getValue());//get the Password!
		    
		    ///searching the information!
		    q.setName("column_information");
		    q.setColumnFamily(cf);
		    QueryResult<HColumn<String, String>> r4 = q.setKey(instance.getId())
		        .execute();
		    assertNotNull(r4);
		    HColumn<String, String> c4 = r4.get();
		    assertNotNull(c4);
		    instance.setInformation(c4.getValue());//get the information!
		    System.out.println("User_name:"+instance.getName());
		    System.out.println("User_id:"+instance.getId());
		    System.out.println("User_account:"+instance.getAccount());
		    System.out.println("User_password:"+instance.getPassword());
		    System.out.println("User_information:"+instance.getInformation());
		    return instance;
		   
		}catch(RuntimeException re){
			log.debug("find failed");
			throw re;
		}
	}
}
