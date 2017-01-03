package com.mullet.backend.entity;

import com.mullet.backend.util.UUIDGenerator;

public class RoleRef {

	private String id;
	private String roleId;
	private String refId;
	
	public RoleRef(String roleId, String refId){
		this.id = UUIDGenerator.getUUID();
		this.roleId = roleId;
		this.refId = refId;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

}
