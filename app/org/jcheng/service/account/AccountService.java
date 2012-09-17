package org.jcheng.service.account;

/**
 * An API to manage user accounts with a distributed datastore.
 * 
 * <p>Assumes the account backend is a distributed, non-transactional, quorum-write, last write wins datatstore. 
 * A key corollary of the assumption is that the datastore is not consistent and relies on the application to keep 
 * consistency. 
 * </p>
 * 
 * 
 * @author jcheng
 *
 */
public interface AccountService {

	boolean isAccountActive(String username);

	/**
	 * Creates an account.
	 * 
	 * @param username The account name.
	 * @param pwHash The hashed password, may be null or empty.
	 * @param pwHashAlgo The password hash algo, may be null or empty.
	 * @return
	 */
	boolean createAccount(String username, String pwHash, String pwHashAlgo);
	
	boolean setAccountActive(String username, boolean active);
	
	/**
	 * Stores the password hash that has  been generated using the specified algorithm.
	 * 
	 * @param username
	 * @param pwHash
	 * @param pwHashAlgo
	 * @return
	 */
	boolean setAccountLogin(String username, String pwHash, String pwHashAlgo);
	
	/**
	 * Validates user login.
	 * 
	 * It is assumed that the caller of the API will create a password hash (`pwHash`) using the algorithm 
	 * specified by an password algorithm. The API simply compares the provided the pwHash against what's
	 * stored in the datastore.
	 * 
	 * It is possible that the caller is using a brand new hash algo that is different thant he one used to
	 * generate his last known pwHash. If so, his call simply return false, even if the pwHash is correctly 
	 * generated from the new algorithm. This means, if hash algo has been changed on a system level,
	 * a user must first reset his password before this API will work again. 
	 * 
	 * @param username
	 * @param pwHash
	 * @return
	 */
	boolean isLoginValid(String username, String pwHash);
	
	/**
	 * Gets the algorithm used to hash the account password.
	 * 
	 * The returned value is the algorithm used in the last 'setAccountLogin()' call. If the account has no
	 * login, then a default algorithm name will be returned. 
	 * 
	 * @param username Username of the account
	 * @return Algorithm name, never returns `null`.
	 */
	String getPasswordHashAlgo(String username);
	
	boolean removeAccount(String username);
	
	boolean clearAll();
	
	long getCount();

}