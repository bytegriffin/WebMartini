package com.bytegriffin.webmartini.domain;

public class UserMessage {
	
	public static final String UNREAD_STATUS = "0";
	public static final String READED_STATUS = "1";
	
	private String id;
	private String sendId;
	private String recId;
	private String sendTime;
	private String msgId;
	private String status;//0：未读  1：已读 
	private String loginName;
	private String avatar;
	private String title;
	private String content;
	private String attachment1;
	private String attachment2;
	private String attachment3;

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
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
	public String getSendId() {
		return sendId;
	}
	public void setSendId(String sendId) {
		this.sendId = sendId;
	}
	public String getRecId() {
		return recId;
	}
	public void setRecId(String recId) {
		this.recId = recId;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
