package com.mullet.backend.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.mullet.backend.entity.User;

public final class WebHelper {

	public static Session getSession() {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
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
	
	public static void removeSession(Object key) {
		Session session = getSession();
		if (null != session) {
			session.removeAttribute(key);
		}
	}
	
	public static void removeLoginSession() {
		removeSession(Constant.SESSION_LOGIN_VERIFYCODE);
		removeSession(Constant.SESSION_FORGET_VERIFYCODE);
	}

	public static User getUserFromSession() {
		Session session = getSession();
		User user = (User) session.getAttribute(Constant.SESSION_CURRENTUSER);
		return user;
	}
	
	public static String getUserIdFromSession() {
		Session session = getSession();
		User user = (User) session.getAttribute(Constant.SESSION_CURRENTUSER);
		return user.getId();
	}

	public static void setSession(Object key, Object value) {
			Session session = getSession();
			if (null != session) {
				session.setAttribute(key, value);
			}
	}

	public static void setUserSession(User user) {
		setSession(Constant.SESSION_CURRENTUSER, user);
	}

}
