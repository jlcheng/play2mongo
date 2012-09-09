/**
 * 
 */
package org.jcheng.service.account;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
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
			for (String serverAddress : serverAddresses.split(",") ) {
				addrs.add(new ServerAddress(serverAddress.trim()));
			}
			this.conn = new Mongo(addrs);
			this.db = conn.getDB(dbName.trim());
			accountCollection = db.getCollection(collectionName.trim());
			accountCollection.setWriteConcern(WriteConcern.SAFE);
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
        BasicDBObject ref = new BasicDBObject(Fields.USERNAME, username);
        DBCursor cursor = getAccountCollection().find(ref).sort(Sort.BY_ID);
        if ( cursor.hasNext() ) {
        	DBObject account = getAccountCollection().find(ref).sort(Sort.BY_ID).next();
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
        BasicDBObject q = new BasicDBObject(Fields.USERNAME, username);
        DBCursor cursor = accountCollection.find(q).sort(Sort.BY_ID);
        if ( !cursor.hasNext() ) {
        	q.put(Fields.ACTIVE, false);
        	BasicDBObject create = new BasicDBObject(Fields.USERNAME, username);
        	create.put(Fields.ACTIVE, false);
        	getAccountCollection().update(new BasicDBObject(Fields.USERNAME, username), create, true, false);
        	return true;
        }
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org.jcheng.service.account.AccountService#isLoginValid(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isLoginValid(String username, String password) {
		return false;
	}

	@Override
	public boolean removeAccount(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setAccountActive(String username, boolean active) {
    	accountCollection.findAndModify(new BasicDBObject(Fields.USERNAME, username), 
				 new BasicDBObject(Fields._SET, new BasicDBObject(Fields.ACTIVE, active)));
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
}
