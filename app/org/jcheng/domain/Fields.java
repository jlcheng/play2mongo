package org.jcheng.domain;




/**
 * Constant fields for the Account ODM.
 * 
 * This class define constants that help maps MongoDB field names to application domain names, e.g., 
 * a Java member called "username" can be mapped to "uname" in MongoDB.
 * 
 * @author jcheng
 *
 */
public final class Fields {
	
	public static final String USERNAME = "uname";
	public static final String PASSWORD_HASH = "pw";
	public static final String PASSWORD_HASH_ALGO = "pwalg";
	public static final String ACTIVE = "actv";
	public static final String USER_AUTH_TOKEN = "uatkn";

	public static final String LUCKY_COLOR = "lclr";
}
