/**
 * 
 */
package org.jcheng.util.mongo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bson.BSONObject;

import com.mongodb.DBObject;

/**
 * Immutable DBObject useful as constants or shared references.
 * 
 * @author jcheng
 *
 */
public class ImmutableDBObject implements DBObject {
	
	private final Map<String, Object> data;
	
	public ImmutableDBObject(String key, Object v) {
		Map<String, Object> tmpData = new HashMap<String, Object>();
		tmpData.put(key, v);
		this.data = Collections.unmodifiableMap(tmpData);
	}
	
	public ImmutableDBObject(Map<String, Object> sourceData) {
		Map<String, Object> tmpData = new HashMap<String, Object>(sourceData);
		this.data = Collections.unmodifiableMap(tmpData);
	}

	public ImmutableDBObject(DBObject source) {
		Map<String, Object> tmpData = new HashMap<String, Object>();
		for ( String key : source.keySet() ) {
			tmpData.put(key, source.get(key));
		}
		this.data = Collections.unmodifiableMap(tmpData);
	}

	/* (non-Javadoc)
	 * @see org.bson.BSONObject#put(java.lang.String, java.lang.Object)
	 */
	@Override
	public Object put(String key, Object v) {
		throw new RuntimeException("Operation not supported.");
	}

	/* (non-Javadoc)
	 * @see org.bson.BSONObject#putAll(org.bson.BSONObject)
	 */
	@Override
	public void putAll(BSONObject o) {
		throw new RuntimeException("Operation not supported.");
	}

	/* (non-Javadoc)
	 * @see org.bson.BSONObject#putAll(java.util.Map)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void putAll(Map m) {
		throw new RuntimeException("Operation not supported.");
	}

	/* (non-Javadoc)
	 * @see org.bson.BSONObject#get(java.lang.String)
	 */
	@Override
	public Object get(String key) {
		return data.get(key);
	}

	/* (non-Javadoc)
	 * @see org.bson.BSONObject#toMap()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map toMap() {
		return data;
	}

	/* (non-Javadoc)
	 * @see org.bson.BSONObject#removeField(java.lang.String)
	 */
	@Override
	public Object removeField(String key) {
		throw new RuntimeException("Operation not supported.");
	}

	/* (non-Javadoc)
	 * @see org.bson.BSONObject#containsKey(java.lang.String)
	 */
	@Override
	@Deprecated
	public boolean containsKey(String s) {
		return data.containsKey(s);
	}

	/* (non-Javadoc)
	 * @see org.bson.BSONObject#containsField(java.lang.String)
	 */
	@Override
	public boolean containsField(String s) {
		return data.containsKey(s);
	}

	/* (non-Javadoc)
	 * @see org.bson.BSONObject#keySet()
	 */
	@Override
	public Set<String> keySet() {
		return data.keySet();
	}

	/* (non-Javadoc)
	 * @see com.mongodb.DBObject#markAsPartialObject()
	 */
	@Override
	public void markAsPartialObject() {
		throw new RuntimeException("Operation not supported.");
	}

	/* (non-Javadoc)
	 * @see com.mongodb.DBObject#isPartialObject()
	 */
	@Override
	public boolean isPartialObject() {
		return false;
	}

}
