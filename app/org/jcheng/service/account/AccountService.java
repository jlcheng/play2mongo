package org.jcheng.service.account;

/**
 * An API to manage user accounts with a distributed data store.
 * 
 * <p>This API assumes the backend is a distributed, non-transactional, quorum-write, last write wins data store.
 * There are some challenges associated with these assumptions. For one, it is non-trivial to completely remove an
 * Account and all related data objects with one update (updates to multiple objects are non-transactional).  
 * </p>
 * 
 * 
 * @author jcheng
 *
 */
public interface AccountService {

	/**
	 * Is an account active?
	 * 
	 * @param username The account name.
	 * @return
	 */
	boolean isAccountActive(String username);
	
	/**
	 * Sets whether an account is active or not.
	 * 
	 * @param username The account name.
	 * @param active The active flag.
	 * @return
	 */
	boolean setAccountActive(String username, boolean active);	

	/**
	 * Creates an account.
	 * 
	 * @param username The account name.
	 * @param pwHash The hashed password, may be null or empty.
	 * @param pwHashAlgo The password hash algo, may be null or empty.
	 * @return
	 */
	boolean createAccount(String username, String pwHash, String pwHashAlgo);
		
	/**
	 * Stores the password hash that has  been generated using the specified algorithm.
	 * 
	 * @param username The account name.
	 * @param pwHash The hashed password, may be null or empty.
	 * @param pwHashAlgo The password hash algo, may be null or empty.
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
	 * @param username The account name.
	 * @param pwHash The hashed password, may be null or empty.
	 * @return
	 */
	boolean isLoginValid(String username, String pwHash);
	
	/**
	 * Gets the algorithm used to hash the account password.
	 * 
	 * The returned value is the algorithm used in the last 'setAccountLogin()' call. If the account has no
	 * login, then a default algorithm name will be returned. 
	 * 
	 * @param username The account name.
	 * @return Algorithm name, never returns `null`.
	 */
	String getPasswordHashAlgo(String username);
	
	/**
	 * Removes an account from the data store.
	 * 
	 * <p>This call only removes the base account object. Data objects related to an account may have to be 
	 * removed separately.</p>
	 * 
	 * @param username The account name.
	 * @return
	 */
	boolean removeAccount(String username);
	
	
	/**
	 * Removes all accounts from the data store.
	 * 
	 * <p>This call may not remove all accounts when the data store is sharded.</p>
	 * 
	 * @return
	 */
	boolean removeAll();

	/**
	 * Gets the number of accounts in the data store (including inactive accounts).
	 *  
	 * <p>This returned value will be an estimate when the data store is sharded.</p>
	 *  
	 * @return
	 */
	long getCount();

}