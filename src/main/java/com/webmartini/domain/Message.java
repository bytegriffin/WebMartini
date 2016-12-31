package com.webmartini.domain;

public class Message {

	public static final String DRAFTS_STATUS = "0";
	public static final String SEND_STATUS = "1";

	public static final String ALL_USER_RECID = "0";

	private String id;
	private String content;
	private String recId;//0：表示全体用户接收  null：表示按照group或role来接收
	private String sendId;
	private String sendTime;
	private String title;
	private String saveTime;
	private String status;//0：草稿箱   1：已发送   
	private String groupIds;
	private String roleIds;
	private String attachment1;
	private String attachment2;
	private String attachment3;
	private String loginName;//formbean　不是字段　用于展示层
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSendId() {
		return sendId;
	}
	public void setSendId(String sendId) {
		this.sendId = sendId;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSaveTime() {
		return saveTime;
	}
	public void setSaveTime(String saveTime) {
		this.saveTime = saveTime;
	}
	public String getRecId() {
		return recId;
	}
	public void setRecId(String recId) {
		this.recId = recId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	public String getAttachment1() {
		return attachment1;
	}
	public void setAttachment1(String attachment1) {
		this.attachment1 = attachment1;
	}
	public String getAttachment2() {
		return attachment2;
	}
	public void setAttachment2(String attachment2) {
		this.attachment2 = attachment2;
	}
	public String getAttachment3() {
		return attachment3;
	}
	public void setAttachment3(String attachment3) {
		this.attachment3 = attachment3;
	}

}
