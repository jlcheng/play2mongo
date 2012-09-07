/**
 * 
 */
package org.jcheng.service.account;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

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
	
	@PostConstruct
	public void setup() {
		try {
			ArrayList<ServerAddress> addrs = new ArrayList<ServerAddress>();
			for (String serverAddress : serverAddresses.split(",") ) {
				addrs.add(new ServerAddress(serverAddress));
			}
			Mongo m = new Mongo(addrs);
			DB db = m.getDB(dbName);
			accountCollection = db.getCollection(collectionName);
		} catch ( Exception e ) {
			throw new RuntimeException(e);
		}

	}
	
	/* (non-Javadoc)
	 * @see org.jcheng.service.account.AccountService#isAccountActive(java.lang.String)
	 */
	@Override
	public boolean isAccountActive(String username) {
        BasicDBObject ref = new BasicDBObject();
        ref.put(Fields.USERNAME, username);
        DBCursor cursor = accountCollection.find(ref).sort(new BasicDBObject(Fields._ID, 1));
        if ( cursor.hasNext() ) {
        	DBObject account = accountCollection.find(ref).sort(new BasicDBObject(Fields._ID, 1)).next();
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
        BasicDBObject ref = new BasicDBObject();
        ref.put(Fields.USERNAME, username);
        ref.put(Fields.ACTIVE, Boolean.FALSE);
        getAccountCollection().insert(ref);
		return true;
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
