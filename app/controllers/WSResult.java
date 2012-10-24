package controllers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Objects;

/**
 * Models a simple web service result call.
 * 
 * @author jcheng
 *
 */
public class WSResult {
	
	private boolean success = false;
	private final List<String> messages = new ArrayList<String>();
	private final Map<String, Object> attributes = new LinkedHashMap<String, Object>();
	
	public WSResult() {
		
	}
	
	/**
	 * A response without any detailed message.
	 * 
	 * @param success successful or failed
	 */
	public WSResult(boolean success) {
		this.success = success;
	}	

	/**
	 * A response with a single message.
	 * 
	 * @param success successful or failed
	 * @param message a message to the client 
	 */
	public WSResult(boolean success, String message) {
		this.success = success;
		this.messages.add(message);
	}
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
				.add("messages", messages)
				.add("attributes", attributes)
				.toString();
	}
	
	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
	
	/**
	 * @param success the success to set
	 */
	public WSResult setSuccess(boolean success) {
		this.success = success;
		return this;
	}
	
	/**
	 * @return the messages
	 */
	public List<String> getMessages() {
		return messages;
	}

	/**
	 * @return the attributes
	 */
	public Map<String, Object> getAttributes() {
		return attributes;
	}

}
