package com.mullet.backend.interceptor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.mullet.backend.entity.User;
import com.mullet.backend.service.UserService;
import com.mullet.backend.util.Constant;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.cache.ehcache.EhCache;

public class ShiroRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	private EhCache<String, AtomicInteger> passwordRetryCache;

	public ShiroRealm() {
		super();
	}

	public void removePasswordRetryCache(String value){
		passwordRetryCache.remove(value);
	}

	/**
	 * 认证回调函数, 登录时调用.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {

		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String value = token.getUsername();
		DefaultWebSecurityManager securityManager =	(DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
		SecurityUtils.setSecurityManager(securityManager);

		EhCacheManager cacheManager =  (EhCacheManager) securityManager.getCacheManager();
		passwordRetryCache = (EhCache) cacheManager.getCache(Constant.SHIRO_PASSWORD_RETRY_CACHE);
		AtomicInteger count = passwordRetryCache.get(value);
		if(count == null) {
			 count = new AtomicInteger(0);		 
		}
		passwordRetryCache.put(value, count);
		// retry count + 1
		int retryCount = count.incrementAndGet();

		if (retryCount > 5) {
			// if retry count > 5 throw
			throw new ExcessiveAttemptsException();
		}

		String pwd = String.valueOf(token.getPassword());
		User user = userService.getUserByUserName(value);
		if(user == null){
			throw new UnknownAccountException();
		}
		if (!pwd.equals(user.getPassword())) {
			throw new IncorrectCredentialsException();
		}
		if(User.LOCK_STATUS.equals(user.getStatus())){
			throw new LockedAccountException();
		}

		SimpleAuthenticationInfo simpleAuthenticationInfo =  getSimpleAuthenticationInfo(value, user.getPassword());
		return simpleAuthenticationInfo;
	}

	public SimpleAuthenticationInfo getSimpleAuthenticationInfo(String username, String password){
		return new SimpleAuthenticationInfo(username, password, ByteSource.Util.bytes(Constant.SHIRO_SALT), getName());
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) throws AuthorizationException{
		String username = (String) getAvailablePrincipal(principals);
		return getSimpleAuthorizationInfo(username);
	}

	public SimpleAuthorizationInfo getSimpleAuthorizationInfo(String username){
		User user = userService.getUserByUserName(username);
		List<String> userperm = userService.getPermissions(user.getId());
		/* 这里编写授权代码 */
		Set<String> permissions = new HashSet<String>();
		permissions.add(Constant.SHIRO_PERMISSION_COMMON);
		permissions.addAll(userperm);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permissions);
		return info;
	}

	/**
	 * 更新当前用户认证缓存
	 * @param password
	 */
	public void updateAuthenticationCache(String password) {
		Subject currentUser = SecurityUtils.getSubject();
		String username = (String) currentUser.getPrincipal();
		clearCachedAuthenticationInfo(username);
		Cache<Object, AuthenticationInfo> authenticationCache = getAuthenticationCache();
		authenticationCache.put(username, getSimpleAuthenticationInfo(username, password));
	}

	/**
	 * 清空用户认证信息缓存.
	 */
	public void clearCachedAuthenticationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthenticationInfo(principals);
	}

	/**
	 * 更新当前用户授权缓存
	 * @param password
	 */
	public void updateAuthorizationCache(String username) {
		clearCachedAuthorizationInfo(username);
		Cache<Object, AuthorizationInfo> authorizationCache = getAuthorizationCache();
		authorizationCache.put(username, getSimpleAuthorizationInfo(username));
	}
	
	/**
	 * 清空用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除所有用户缓存.
	 */
	public void clearAllCached() {
		Subject currentUser = SecurityUtils.getSubject();
		PrincipalCollection princialCollection = currentUser.getPrincipals(); 
		super.clearCache(princialCollection);
		
		EhCacheManager activeSessionCache =  (EhCacheManager) super.getCacheManager();
		EhCache<Object,Object> sessionCache = (EhCache<Object,Object>) activeSessionCache.getCache(Constant.SHIRO_ACTIVE_SESSION_CACHE);

		if(sessionCache != null){
			for(Object obj : sessionCache.keys()){
				sessionCache.remove(obj);
			}
		}

		Cache<Object, AuthenticationInfo> authticationCache = getAuthenticationCache();
		if (authticationCache != null) {
			for (Object key : authticationCache.keys()) {
				authticationCache.remove(key);
			}
		}
		
		Cache<Object, AuthorizationInfo> authorizationCache = getAuthorizationCache();
		if (authorizationCache != null) {
			for (Object key : authorizationCache.keys()) {
				authorizationCache.remove(key);
			}
		}
		
	}



}