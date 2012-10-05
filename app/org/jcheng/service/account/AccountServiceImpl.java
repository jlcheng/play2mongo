/**
 * 
 */
package org.jcheng.service.account;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.jcheng.domain.Account;
import org.jcheng.domain.Fields;
import org.jcheng.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link AccountService} using a MongoDB Backend.
 * 
 * @author jcheng
 *
 */
@Component
public class AccountServiceImpl implements AccountService {
	
	private final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class); 
	
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@PostConstruct
	public void init() {
		if ( logger.isDebugEnabled() ) {
			logger.debug("AccountServiceImpl initialized.");
		}
	}
	
	@PreDestroy
	public void close() {
		if ( logger.isDebugEnabled() ) {
			logger.debug("AccountServiceImpl to be destroyed.");
		}
	}
	
	@Override
	public boolean isAccountActive(String username) {
		Account acct = accountRepository.findOneByUsername(username);
		return acct != null && acct.isActive(); 
	}
	
	@Override
	public boolean isAccountCreated(String username) {
		Account acct = getAccount(username);
		return acct != null;
	}
	
	@Override
	public boolean createAccount(String username, String pwHash, String pwHashAlgo) {
		Account acct = accountRepository.findOneByUsername(username);
		if ( acct == null ) {
			Account newAccount = new Account();
			newAccount.setUsername(username);
			newAccount.setPwHash(pwHash);
			newAccount.setPwHashAlgo(pwHashAlgo);
			acct = accountRepository.save(newAccount);
			return acct != null && acct.getId() != null;			
		}
		return false;
	}

	@Override
	public void setAccountActive(String username, boolean active) {
		Account acct = getAccount(username);
		if ( acct != null ) {
			acct.setActive(active);
		}
	}

	@Override
	public void removeAll() {
		accountRepository.deleteAll();
	}

	@Override
	public long getCount() {
		return accountRepository.count();
	}


	@Override
	public void setAccountLogin(String username, String pwHash,
			String pwHashAlgo) {
		Account acct = getAccountRepository().findOneByUsername(username);
		if ( acct != null ) {
			acct.setUsername(username);
			acct.setPwHash(pwHash);
			acct.setPwHashAlgo(pwHashAlgo);
			getAccountRepository().save(acct);
		}
	}

	@Override
	public boolean isLoginValid(String username, String pwHash) {
		Account acct = getAccount(username);
		return acct != null && acct.getPwHash() != null && acct.getPwHash().equals(pwHash);
	}

	@Override
	public void removeAccount(String username) {
		getMongoTemplate().remove(new Query(Criteria.where(Fields.USERNAME).is(username)), Account.class);
	}

	@Override
	public String getPasswordHashAlgo(String username) {
		Account acct = getAccount(username);
		if ( acct != null ) {
			return acct.getPwHashAlgo();
		}
		return null;
	}
	
	/**
	 * Gets an Account by username.
	 * 
	 * @param username The username.
	 * @return An Account instance.
	 */
	protected Account getAccount(String username) {
		return accountRepository.findOneByUsername(username);
	}

	/**
	 * @return the accountRepository
	 */
	public AccountRepository getAccountRepository() {
		return accountRepository;
	}

	/**
	 * @param accountRepository the accountRepository to set
	 */
	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	/**
	 * @return the mongoTemplate
	 */
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	/**
	 * @param mongoTemplate the mongoTemplate to set
	 */
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

}
