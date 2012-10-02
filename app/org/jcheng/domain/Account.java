package org.jcheng.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import com.google.common.base.Objects;

public class Account {

	@Id
	private String id;
	
	@Field("uname")
	private String username;
	
	@Field("actv")
	private boolean active;
	
	@Override
	public String toString() {
		return Objects.toStringHelper(this)
		    .add("id", id)
			.add("username", username)
			.add("active", active)
			.toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}


}
