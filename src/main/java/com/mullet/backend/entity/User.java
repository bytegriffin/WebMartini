package com.mullet.backend.entity;

import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class User implements java.io.Serializable {

	private static final long serialVersionUID = 2882794718762953397L;
	public static final String ACTIVE_STATUS = "1";// 正常
	public static final String LOCK_STATUS = "2";// 锁定
	private String id;
	private String phone;
	private String name;
	private String loginName;
	private String password;
	private String sex;//null:保密  0:女  1:男
	private String email;
	private String birthday;
	private String avatar;
	private String home;
	private String postcode;
	private String idNumber;
	private String address;
	private Date registTime;
	private Date loginTime;
	private String status;
	private String loginIP;
	private int loginCount;
	private boolean checked;//多选框是否被选中
	private Set<String> groupNames;
	private Set<String> roleNames;
	private Set<String> permissions;//菜单url
	private String groupNamesStr;//groupNames字段的str版
	private String roleNamesStr;//roleNames字段的str版

	public String getSexStr(String sex){
		if(sex == null || sex.equals("")){
			return "保密";
		}else if(sex.equals("0")){
			return "女";
		}else if(sex.equals("1")){
			return "男";
		}
		return "保密";
	}

	public String getGroupNamesStr() {
		return groupNamesStr;
	}

	public void setGroupNamesStr(String groupNamesStr) {
		this.groupNamesStr = groupNamesStr;
	}

	public String getRoleNamesStr() {
		return roleNamesStr;
	}

	public void setRoleNamesStr(String roleNamesStr) {
		this.roleNamesStr = roleNamesStr;
	}

	public Set<String> getGroupNames() {
		return groupNames;
	}

	public void setGroupNames(Set<String> groupNames) {
		this.groupNames = groupNames;
	}

	public Set<String> getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(Set<String> roleNames) {
		this.roleNames = roleNames;
	}

	public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}

	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLoginIP() {
		return loginIP;
	}

	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}



	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getRegistTime() {
		return registTime;
	}

	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getLoginTime() {
		return loginTime;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// @Override
	// public String toString() {
	// return "UserEntity [id=" + id + ", userName=" + userName + ", password="
	// +
	// password + ", sex=" + sex
	// + ", email=" + email + "]";
	// }

}
