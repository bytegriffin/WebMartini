package com.mullet.backend.util;

public final class Constant {

	public static final String APPLICATION_PROPERTIES_FILE = "config/application.properties";	

	public static final String USERLOG_INTERVAL_KEY = "userlog.interval";

	public static final String USERLOG_ERROR_INTERVAL_KEY = "userlog.error.interval";

	public static String USERLOG_INTERVAL_VALUE;

	public static String USERLOG_ERROR_INTERVAL_VALUE;
	
	public static final String SHIRO_ACTIVE_SESSION_CACHE = "shiro-activeSessionCache";
	
	public static final String SHIRO_PASSWORD_RETRY_CACHE = "passwordRetryCache";

	public static final String SESSION_LOGIN_VERIFYCODE = "login_VerifyCode";// 登录图片验证码

	public static final String SESSION_FORGET_VERIFYCODE = "forget_VerifyCode";// 忘记密码图片验证码

	public static final String SHIRO_PERMISSION_COMMON = "main";// 全部用户共同拥有

	public static final String SESSION_CURRENTUSER = "currentUser";

	public static final String SHIRO_SALT = "mullet";

	public static final String AVATAR_UPLOAD_PATH = "static/upload/avatar/";
	
	public static final String ATTACHMENT_UPLOAD_PATH = "static/upload/attachment/";

	public static final String USER_AGENT = "User-Agent";

	public static final String SESSION_USERNAME = "username";

}
