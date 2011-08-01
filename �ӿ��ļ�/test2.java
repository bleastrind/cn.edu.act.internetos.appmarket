import me.prettyprint.hector.api.ApiV2SystemTest;
import me.prettyprint.hector.api.ApiV2SystemTest.TestCleanupDescriptor;
public class test2 {

	public static void main(String args[]){
		
		//test.insertColumns("test", 3, "key", 4, "column_name");
		
		App instance = new App();
		AppDao t1 = new AppDao();
		User instance2 = new User();
		UserDao u1 = new UserDao();
		UserSpace instance3 = new UserSpace();
		UserSpaceDao us1 = new UserSpaceDao();
		/*instance.setId("1");
		instance.setName("人人");
		instance.setInformation("人人网是一个不错的网上交流平台");
		instance2.setId("2");
		instance2.setAccount("dongjizhizhang");
		instance2.setName("张文敏");
		instance2.setPassword("123456");
		instance2.setInformation("G515实习生");
		t1.save(instance);
		u1.save(instance2);*/
		//t2.save2(instance);
		//t1.getAllApps();
		//t1.findById("1");
		//u1.findByAccount("dongjizhizhang");
		//t1.delete(instance);
		//u1.delete(instance2);*/
		instance3.setId("3");
		//instance3.setAppid("1");
		for(int i=0;i<5; i++){instance3.setAppidList(i, "123");}
		us1.save(instance3);
		us1.getUserSpace(instance3);
		//ApiV2SystemTest test = new ApiV2SystemTest();
		
		//test.deleteColumns(cleanup);
		//TestCleanupDescriptor cleanup = test.insertColumns("test", 3, "key", 4, "column_name");
		//TestCleanupDescriptor t2 = new TestCleanupDescriptor();
		//test.testRange(cleanup);
	}
	
}
