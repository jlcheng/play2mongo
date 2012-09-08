/**
 * 
 */
package org.jcheng.service.account;

import org.jcheng.util.mongo.ImmutableDBObject;

/**
 * @author jcheng
 *
 */
public final class Sort {
	
	private Sort() {
		
	}
	
	public static final ImmutableDBObject BY_ID = new ImmutableDBObject(Fields._ID, 1);

}
