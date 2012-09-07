/**
 * 
 */
package org.jcheng.service.account;

import org.jcheng.util.test.SetupUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author jcheng
 *
 */
public class UserTest {
	
	private AccountService accountService = null;
	
	@Before
	public void setup() {
		SetupUtils.setup();
		accountService = SetupUtils.getApplicationContext().getBean(AccountService.class);
	}
	
	@Test
	public void testMe() throws Exception {
		/*
		Mongo m = new Mongo(Arrays.asList(new ServerAddress("localhost")));
		DB db = m.getDB("test");
		DBCollection users = db.getCollection("accounts");
        BasicDBObject doc = new BasicDBObject();
        doc.put("uname", "jcheng");
        users.insert(doc);
        */
        //accountService.createAccount("jcheng");
		accountService.clearAll();
		accountService.createAccount("jcheng");
        System.out.println("is Account active: " + accountService.isAccountActive("jcheng"));
        accountService.setAccountActive("jcheng", true);
        System.out.println("is Account active: " + accountService.isAccountActive("jcheng"));
        
	}
	
	@After
	public void teardown() {
		SetupUtils.teardown();
	}

}
