/**
 * 
 */
package org.jcheng.service.account;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.jcheng.service.ServiceUtils;
import org.jcheng.util.mongo.ImmutableDBObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

/**
 * @author jcheng
 *
 */
@Component
public class AccountServiceImpl implements AccountService {
	
	@Value("#{'${accountDb.hostName}'}")	
	private String serverAddresses;
	@Value("#{'${accountDb.dbName}'}")		
	private String dbName;
	@Value("#{'${accountDb.collectionName}'}")	
	private String collectionName;
	private DBCollection accountCollection;
	private DB db;
	private Mongo conn;
	
	@PostConstruct
	public void setup() {
		try {
			ArrayList<ServerAddress> addrs = new ArrayList<ServerAddress>();
			Splitter s = Splitter.on(',').trimResults().omitEmptyStrings();
			for (String serverAddress : s.split(serverAddresses) ) {
				addrs.add(new ServerAddress(serverAddress));
			}
			this.conn = new Mongo(addrs);
			this.db = conn.getDB(Strings.nullToEmpty(dbName).trim());
			this.accountCollection = db.getCollection(Strings.nullToEmpty(collectionName).trim());
			this.accountCollection.setWriteConcern(WriteConcern.SAFE);
		} catch ( Exception e ) {
			throw new RuntimeException(e);
		}
	}
	
	@PreDestroy
	public void tearDown() {
		if ( this.conn != null ) {
			conn.close();
		}
	}
	
	/* (non-Javadoc)
	 * @see org.jcheng.service.account.AccountService#isAccountActive(java.lang.String)
	 */
	@Override
	public boolean isAccountActive(String username) {
		ImmutableDBObject q = new ImmutableDBObject(Fields.USERNAME, username);
		DBObject fields = Fields.O_ACTIVE;
    	DBObject account = getAccountCollection().findOne(q, fields);
        if ( account != null ) {
        	Object active = account.get(Fields.ACTIVE);
        	return Boolean.TRUE.equals(active);
        }
        return false;
	}
	
	/* (non-Javadoc)
	 * @see org.jcheng.service.account.AccountService#createAccount(java.lang.String)
	 */
	@Override
	public boolean createAccount(String username) {
		ImmutableDBObject q = new ImmutableDBObject(Fields.USERNAME, username);
		DBObject dbo = getAccountCollection().findOne(q, Fields.O_NATIVE_ID);
        if ( dbo == null ) {
        	DBObject newAccount = BasicDBObjectBuilder
			        			.start(Fields.USERNAME, username)
			        			.append(Fields.ACTIVE, false)
			        			.get();
        	getAccountCollection().update(q, newAccount, true, false);
        	return true;
        }
		return false;
	}

	@Override
	public boolean setAccountActive(String username, boolean active) {
		ImmutableDBObject query = new ImmutableDBObject(Fields.USERNAME, username);
		DBObject fields = new ImmutableDBObject(Fields.ACTIVE, active);
    	accountCollection.findAndModify(query, new BasicDBObject(Fields._SET, fields));		
        return true;
	}

	@Override
	public boolean clearAll() {
		getAccountCollection().remove(new BasicDBObject());
		return true;
	}

	@Override
	public long getCount() {
		return getAccountCollection().count();
	}


	@Override
	public boolean setAccountLogin(String username, String pwHash,
			String pwHashAlgo) {
		ImmutableDBObject query = new ImmutableDBObject(Fields.USERNAME, username);
		DBObject fields = BasicDBObjectBuilder
							.start(Fields.PASSWORD_HASH, pwHash)
							.append(Fields.PASSWORD_HASH_ALGO, pwHashAlgo)
						  .get();
    	accountCollection.findAndModify(query, new BasicDBObject(Fields._SET, fields));		
		return false;
	}

	/* (non-Javadoc)
	 * @see org.jcheng.service.account.AccountService#isLoginValid(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isLoginValid(String username, String pwHash) {
        DBObject q = BasicDBObjectBuilder
        				.start(Fields.USERNAME, username)
        				.add(Fields.PASSWORD_HASH, pwHash).get();
        DBObject retval = getAccountCollection().findOne(q);
        return retval != null;
	}

	@Override
	public boolean removeAccount(String username) {
		return false;
	}

	/**
	 * @return the accountCollection
	 */
	public DBCollection getAccountCollection() {
		return accountCollection;
	}

	/**
	 * @param accountCollection the accountCollection to set
	 */
	public void setAccountCollection(DBCollection accountCollection) {
		this.accountCollection = accountCollection;
	}

	@Override
	public String getPasswordHashAlgo(String username) {
        DBObject q = new BasicDBObject(Fields.USERNAME, username);
        DBObject dbo = getAccountCollection().findOne(q, Fields.O_PASSWORD_HASH_ALGO);
        String retval = null;
        if ( dbo != null ) {
        	retval = String.valueOf(dbo.get(Fields.PASSWORD_HASH_ALGO));
        }
        if ( Strings.isNullOrEmpty(retval) ) {
        	retval = ServiceUtils.SHA_256;
        }
		return retval;
	}

}
