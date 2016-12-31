package com.webmartini.domain;

import com.webmartini.util.UUIDGenerator;

public class UserRef {
	
	private String id;
	private String userId;
	private String refId;
	
	public UserRef(String userId, String refId){
		this.id = UUIDGenerator.getUUID();
		this.userId = userId;
		this.refId = refId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

}
