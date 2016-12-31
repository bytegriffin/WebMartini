package com.webmartini.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.webmartini.domain.User;

public final class WebHelper {

	private static final String SESSION_LOGIN_VERIFYCODE = "login_VerifyCode";// 登录图片验证码
	private static final String SESSION_FORGET_VERIFYCODE = "forget_VerifyCode";// 忘记密码图片验证码
	public static final String SESSION_CURRENTUSER = "currentUser";

	private static Session getSession() {
		Subject subject = SecurityUtils.getSubject();
		if (null != subject) {
			Session session = subject.getSession();
			return session;
		}
		return null;
	}

	public static Object getSession(Object key) {
		Session session = getSession();
		if (null != session) {
			return session.getAttribute(key);
		}
		return null;
	}

	private static void removeSession(Object key) {
		Session session = getSession();
		if (null != session) {
			session.removeAttribute(key);
		}
	}

	public static void removeLoginSession() {
		removeSession(SESSION_LOGIN_VERIFYCODE);
		removeSession(SESSION_FORGET_VERIFYCODE);
	}

	public static User getUserFromSession() {
		Session session = getSession();
//		System.err.println(session.getTimeout()+"============"+session.getId());
		User user = (User) session.getAttribute(session.getId());
		return user;
	}

	public static String getUserIdFromSession() {
		Session session = getSession();
		User user = (User) session.getAttribute(session.getId());
		return user.getId();
	}

	public static void setSession(Object key, Object value) {
		Session session = getSession();
		if (null != session) {
			session.setAttribute(key, value);
		}
	}

	public static void setUserSession(User user) {
		Session session = getSession();
		setSession(session.getId(), user);
	}

}
