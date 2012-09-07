package org.jcheng.service.account;

public interface AccountService {

	boolean isAccountActive(String username);

	boolean createAccount(String username);

	boolean isLoginValid(String username, String password);
	
	boolean removeAccount(String username);

}