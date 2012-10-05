package org.jcheng.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import com.google.common.base.Objects;

public class Account {
	
	@Id
	private String id;
	
	@Indexed(unique = true)	
	@Field(Fields.USERNAME)
	private String username;
	
	@Field(Fields.ACTIVE)
	private boolean active;
	
	@Field(Fields.PASSWORD_HASH)
	private String pwHash;
	
	@Field(Fields.PASSWORD_HASH_ALGO)
	private String pwHashAlgo;
	
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

	public String getPwHash() {
		return pwHash;
	}

	public void setPwHash(String pwHash) {
		this.pwHash = pwHash;
	}

	public String getPwHashAlgo() {
		return pwHashAlgo;
	}

	public void setPwHashAlgo(String pwHashAlgo) {
		this.pwHashAlgo = pwHashAlgo;
	}


}
