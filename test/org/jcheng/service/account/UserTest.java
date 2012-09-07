/**
 * 
 */
package org.jcheng.service.account;

import java.util.Arrays;

import org.jcheng.util.test.SetupUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

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
        System.out.println("is Account active: " + accountService.isAccountActive("jcheng"));
	}
	
	@After
	public void teardown() {
		SetupUtils.teardown();
	}

}
