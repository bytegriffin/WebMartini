package com.bytegriffin.webmartini.domain;

import com.bytegriffin.webmartini.util.UUIDGenerator;

public class GroupRef {

	private String id;
	private String groupId;
	private String refId;//roleid or userid
	
	public GroupRef(String groupId, String refId){
		this.id = UUIDGenerator.getUUID();
		this.groupId = groupId;
		this.refId = refId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	
	
}
