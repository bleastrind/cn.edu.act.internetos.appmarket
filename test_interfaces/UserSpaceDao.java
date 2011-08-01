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

/*
 * the methods for the UserSpace
 */
public class UserSpaceDao {
	final static String KEYSPACE = "Keyspace1";
	static final StringSerializer se = new StringSerializer();
	Cluster cluster = getOrCreateCluster("Test Cluster", "127.0.0.1:9160");
	Keyspace ko = createKeyspace(KEYSPACE, cluster);
	Mutator<String> m = createMutator(ko,se);
	String cf = "UserSpaceTestTable";
	static final Logger log = LoggerFactory.getLogger(UserSpaceDao.class);
	
	void save(UserSpace instance){
		log.debug("saving UserSpace instance");
		try{
			
			MutationResult mr = m.insert(instance.getId(), cf, createColumn("column_name",instance.getAppidList("0"),se,se));
			log.debug("insert execution time: {}", mr.getExecutionTimeMicro());
			
		}catch(RuntimeException re){
			log.debug("save failed");
			throw re;
		}
	}
	
	UserSpace getUserSpace(UserSpace user){
		log.debug("find a user");
		try{
			//At first,searching the id through the id
			UserSpace instance = new UserSpace();
			//instance.setId("1");
			
			ColumnQuery<String, String, String> q = createColumnQuery(ko, se, se, se);
		    q.setName("column_name");
		    q.setColumnFamily(cf);
		    QueryResult<HColumn<String, String>> r = q.setKey(user.getId())
		        .execute();
		    assertNotNull(r);
		    //instance.setAccount(account);//set accout;
		    HColumn<String, String> c = r.get();
		    assertNotNull(c);
		    instance.setId(user.getId());
		    instance.setAppidList(0,c.getValue());//get the appid!
		    System.out.println("userid:"+instance.getId());
		    System.out.println("appid:"+instance.getAppidList("0"));
		    return instance;
		}catch(RuntimeException re){
			log.debug("get failed");
			throw re;
		}
	}
}
