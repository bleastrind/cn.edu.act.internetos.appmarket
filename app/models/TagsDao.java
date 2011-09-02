package models;
import static me.prettyprint.hector.api.factory.HFactory.createColumn;
import static me.prettyprint.hector.api.factory.HFactory.createColumnQuery;
import static me.prettyprint.hector.api.factory.HFactory.createKeyspace;
import static me.prettyprint.hector.api.factory.HFactory.createMutator;
import static me.prettyprint.hector.api.factory.HFactory.getOrCreateCluster;
import static org.junit.Assert.assertNotNull;
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
import java.util.List;
import java.util.ArrayList;
/*
 * the methods for the Tags
 */
public class TagsDao {
	final static String KEYSPACE = "Keyspace1";
	static final StringSerializer se = new StringSerializer();
	static final Logger log = LoggerFactory.getLogger(TagsDao.class);
	
	Cluster cluster = getOrCreateCluster("Test Cluster", "127.0.0.1:9160");
	Keyspace ko = createKeyspace(KEYSPACE, cluster);
	String cf = "TagsTestTable";
	
	Mutator<String> m = createMutator(ko,se);
	
	
	public void save(Tags instance){
		log.debug("saving Tags instance");
		try{
			
			MutationResult mr = m.insert(instance.getId(), cf, createColumn("column_name",listToString(instance.getTags()),se,se));
			log.debug("insert execution time: {}", mr.getExecutionTimeMicro());
			
		}catch(RuntimeException re){
			log.debug("save failed");
			throw re;
		}
	}
	
	public void delete(Tags us, String Tag){
		log.debug("deleting one appid of the Tags");
		try{
			us.getTags().remove(Tag);
			m.insert(us.getId(), cf, createColumn("column_name",listToString(us.getTags()),se,se));
		}catch(RuntimeException re){
			log.debug("delete failed");
			throw re;
		}
	}

	public void delete(Tags instance){
		log.debug("deleting Tags instance");
		try{
			 m.delete(instance.getId(), cf,
						"column_name", se);
		}catch(RuntimeException re){
			log.debug("delete failed");
			throw re;
		}
	}
	
	public Tags getTags(App app){
		log.debug("find a app");
		try{
                           
			//searching the appid through the appid
			
			ColumnQuery<String, String, String> q = createColumnQuery(ko, se, se, se);
		    q.setName("column_name");
		    q.setColumnFamily(cf);
		    QueryResult<HColumn<String, String>> r = q.setKey(app.getId())
		        .execute();
		    assertNotNull(r);
		    HColumn<String, String> c = r.get();
		    assertNotNull(c);
			
			Tags instance = new Tags();
			instance.setId(app.getId());
		    List<String> list = stringToList(c.getValue());
		    instance.setTags(list);//get the appid!
			
		    return instance;
		}catch(RuntimeException re){
			log.debug("get failed");
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
			if (!i.isEmpty())
				list.add(i);
		}
		return list;
	}
}
