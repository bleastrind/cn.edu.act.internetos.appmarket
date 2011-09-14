package models;

import java.util.*;
import static me.prettyprint.hector.api.factory.HFactory.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.mutation.MutationResult;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.*;
import me.prettyprint.cassandra.model.*;
import me.prettyprint.hector.api.beans.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 *一些常用的方法 
 */
public class UserDao {
	final static String KEYSPACE = "AppMarket";
	final static String CF = "User";
	final static StringSerializer se = new StringSerializer();
	
	Cluster cluster = getOrCreateCluster("Test Cluster", "127.0.0.1:9160");
	Keyspace keyspace = createKeyspace(KEYSPACE, cluster);	
	Mutator<String> m = createMutator(keyspace, se);
	
	public void save(User instance){
		m.insert(instance.getId(), CF, createStringColumn("account", instance.getAccount()));
		m.insert(instance.getId(), CF, createStringColumn("password", instance.getPassword()));
		m.insert(instance.getId(), CF, createStringColumn("name", instance.getName()));					
	}
	
	public void delete(User instance){
		m.delete(instance.getId(), CF, null, se);
	}
	
	public User findByAccount(String account){
		User user = null;
		IndexedSlicesQuery<String, String, String> indexedSlicesQuery = createIndexedSlicesQuery(keyspace, se, se, se);
		indexedSlicesQuery.addEqualsExpression("account", account);
		indexedSlicesQuery.setColumnNames("name", "account", "name");
		indexedSlicesQuery.setColumnFamily(CF);
		QueryResult<OrderedRows<String, String, String>> queryResult = indexedSlicesQuery.execute();
		OrderedRows<String, String, String> orderedRows = queryResult.get();
		List<Row<String, String, String>> rows = orderedRows.getList();
		ColumnQuery<String, String, String> columnQuery = createStringColumnQuery(keyspace);
		for (Row<String, String, String> row: rows)
		{			
			user = new User(row.getKey(),
							columnQuery.setColumnFamily(CF).setKey(row.getKey()).setName("account").execute().get().getValue(),
							columnQuery.setColumnFamily(CF).setKey(row.getKey()).setName("password").execute().get().getValue(),
							columnQuery.setColumnFamily(CF).setKey(row.getKey()).setName("name").execute().get().getValue());
		}
		return user;
	}
}
