package org.jcheng.service.account;

import java.util.Random;

import org.jcheng.domain.Account;
import org.jcheng.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

@Component("authorizationService")
public class AuthorizationServiceImpl implements AuthorizationService {
	
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(AuthorizationServiceImpl.class); 
	private final HashFunction sha1 = Hashing.sha1();
	
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private MongoTemplate mongoTemplate;	

	@Override
	public String getToken(String username) {
		Account acct = accountRepository.findOneByUsername(username);
		if ( acct != null ) {
			byte[] rndb = new byte[8];
			new Random().nextBytes(rndb);
			StringBuilder sb = new StringBuilder(username);
			for ( byte b : rndb ) {
				sb.append(b);
			}
			String token = sha1.hashString(sb).toString();
			acct.setAuthorizationToken(token);
			accountRepository.save(acct);
			return token;
		}
		return null;
	}

	@Override
	public boolean validateToken(String username, String token) {
		Account acct = accountRepository.findOneByUsername(username);
		if ( acct != null ) {
			return token != null && token.equals(acct.getAuthorizationToken());
		}
		return false;
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
