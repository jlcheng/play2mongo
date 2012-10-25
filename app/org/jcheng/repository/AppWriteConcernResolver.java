package org.jcheng.repository;

import org.jcheng.domain.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoAction;
import org.springframework.data.mongodb.core.MongoActionOperation;
import org.springframework.data.mongodb.core.WriteConcernResolver;
import org.springframework.stereotype.Component;

import com.mongodb.DBObject;
import com.mongodb.WriteConcern;

@Component("appWriteConcernResolver")
public class AppWriteConcernResolver implements WriteConcernResolver {
	
	private static final Logger logger = LoggerFactory.getLogger(AppWriteConcernResolver.class);

	@SuppressWarnings("rawtypes")
	@Override
	public WriteConcern resolve(MongoAction action) {
		try {
			Class entityClass = action.getEntityClass();
			MongoActionOperation op = action.getMongoActionOperation();
			DBObject dbObject = action.getDocument();
			
			if ( entityClass != null 
					&& op != null && op.equals(MongoActionOperation.SAVE) 
					&& entityClass.equals(Account.class) 
					&& dbObject != null
					&& (dbObject.get("_id") != null) ) {
				// User account creation.
				return WriteConcern.JOURNAL_SAFE;
			}
			return action.getDefaultWriteConcern();
		} catch ( Throwable t ) {
			logger.error("cannot resolve write concern", t);
			return action.getDefaultWriteConcern();
		}
	}

}
