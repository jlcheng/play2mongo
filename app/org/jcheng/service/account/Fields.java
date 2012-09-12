/**
 * 
 */
package org.jcheng.service.account;

import org.jcheng.util.mongo.ImmutableDBObject;



/**
 * @author jcheng
 *
 */
public final class Fields {
	
	public static final String USERNAME = "uname";
	public static final String PASSWORD_HASH = "pw";
	public static final String PASSWORD_HASH_ALGO = "pwalg";
	public static final String _ID = "_id";
	public static final String ACTIVE = "actv";
	public static final String _SET = "$set";
	
	public static final ImmutableDBObject O_NATIVE_ID = new ImmutableDBObject(Fields._ID, 1);
	public static final ImmutableDBObject O_PASSWORD_HASH_ALGO = new ImmutableDBObject(Fields.PASSWORD_HASH_ALGO, 1);
	public static final ImmutableDBObject O_ACTIVE = new ImmutableDBObject(Fields.ACTIVE, 1);
}
