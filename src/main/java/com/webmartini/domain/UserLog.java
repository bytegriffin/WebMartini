package com.webmartini.domain;

import com.webmartini.util.DateUtil;

public class UserLog {

	private String id;
	private String userId;
	private String userName;//loginName or email
	private String operType;
	private String operContent;
	private String exception;
	private String operTime;
	private String operIp;
	private String browser;
	private String os;
	private int httpStatus;
	private String url;// user/list ...
	private String method;//get post
	private String controller; //格式：类名.方法()
	private String days;
	private int count;

	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}
	public String getController() {
		return controller;
	}
	public void setController(String controller) {
		this.controller = controller;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
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

	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public String getOperContent() {
		return operContent;
	}
	public void setOperContent(String operContent) {
		this.operContent = operContent;
	}
	public String getOperTime() {
		return DateUtil.dateToStr(DateUtil.strToDate(operTime));
	}
	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}
	public String getOperIp() {
		return operIp;
	}
	public void setOperIp(String operIp) {
		this.operIp = operIp;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}

}
