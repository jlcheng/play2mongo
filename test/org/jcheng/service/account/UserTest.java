/**
 * 
 */
package org.jcheng.service.account;

import junit.framework.Assert;

import org.jcheng.util.test.SetupUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author jcheng
 *
 */
public class UserTest {
	
	private static final String TEST_USER = "jcheng";
	
	private AccountService accountService = null;
	
	@Before
	public void setup() {
		SetupUtils.setup();
		accountService = SetupUtils.getApplicationContext().getBean(AccountService.class);
	}
	
	@Test
	public void testCreate() throws Exception {
		accountService.clearAll();
		Assert.assertEquals(0, accountService.getCount());
		
		accountService.createAccount(TEST_USER);
		Assert.assertEquals(1, accountService.getCount());
		
        accountService.setAccountActive(TEST_USER, true);
		Assert.assertEquals(true, accountService.isAccountActive(TEST_USER));	
	}
	
	@Test
	public void testSetAccountActive() throws Exception {
		accountService.clearAll();
		accountService.createAccount(TEST_USER);
        accountService.setAccountActive(TEST_USER, true);
		Assert.assertEquals(true, accountService.isAccountActive(TEST_USER));
	}
	
	@Test
	public void testLoginAuthentication() throws Exception {
		//accountService.isLoginValid(TEST_USER, "password");
		accountService.getPasswordHashAlgo(TEST_USER);
	}
	
	
	@After
	public void teardown() {
		SetupUtils.teardown();
	}

}
