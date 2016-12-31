package com.webmartini.init;

import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.DispatcherType;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.webmartini.interceptor.ShiroRealm;

@Configuration
public class ShiroConfig {

	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        // 该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理  
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setDispatcherTypes(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE));
        return filterRegistration;
	}

	@Bean(name = "securityManager")
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setRealm(userRealm());
		manager.setCacheManager(cacheManager());
		manager.setSessionManager(sessionManager());
		manager.setRememberMeManager(rememberMeManager());
//		SecurityUtils.setSecurityManager(manager);
		return manager;
	}

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter() {
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(securityManager());
		bean.setLoginUrl("/");
		bean.setSuccessUrl("/main");
		bean.setUnauthorizedUrl("/error/403");

//		Map<String, Filter> filters = new HashMap<String, Filter>();
//		filters.put("anon", new AnonymousFilter());
//		bean.setFilters(filters);

		Map<String, String> chains = new LinkedHashMap<String, String>();
		chains.put("/", "anon");
		chains.put("/login", "anon");
		chains.put("/login/**", "anon");
		chains.put("/error/**", "anon");
		chains.put("/js/**", "anon");
		chains.put("/css/**", "anon");
		chains.put("/images/**", "anon");
		chains.put("/fonts/**", "anon");
		chains.put("/templates/**", "anon");
		chains.put("/upload/**", "anon");
		chains.put("/error/**", "anon");
		chains.put("/**", "authc");
		bean.setFilterChainDefinitionMap(chains);
		return bean;
	}

	@Bean(name = "sessionManager")
	public DefaultWebSessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setCacheManager(cacheManager());
		sessionManager.setGlobalSessionTimeout(SiteConfig.SHIRO_GLOBAL_SESSION_TIMEOUT);
		sessionManager.setDeleteInvalidSessions(true);
		sessionManager.setSessionValidationSchedulerEnabled(true);
		sessionManager.setSessionDAO(sessionDAO());
		sessionManager.setSessionIdCookieEnabled(true);
		sessionManager.setSessionIdCookie(sessionIdCookie());
		return sessionManager;
	}

	@Bean(name = "sessionIdCookie")
	public SimpleCookie sessionIdCookie() {
		SimpleCookie sc = new SimpleCookie("sid");
		sc.setHttpOnly(true);
		sc.setMaxAge(SiteConfig.SHIRO_COOKIE_MAX_AGE);
		return sc;
	}

	@Bean(name = "rememberMeManager")
	public CookieRememberMeManager rememberMeManager() {
		CookieRememberMeManager remeberMe = new CookieRememberMeManager();
		remeberMe.setCookie(rememberMeCookie());
		return remeberMe;
	}

	@Bean(name = "rememberMeCookie")
	public SimpleCookie rememberMeCookie() {
		SimpleCookie sc = new SimpleCookie("rememberMe");
		sc.setHttpOnly(true);
		sc.setMaxAge(SiteConfig.SHIRO_REMEMBERME_COOKIE_MAX_AGE);
		return sc;
	}

	@Bean(name = "sessionDAO")
	public SessionDAO sessionDAO() {
		EnterpriseCacheSessionDAO sessionDao = new EnterpriseCacheSessionDAO();
		sessionDao.setActiveSessionsCacheName(SiteConfig.SHIRO_ACTIVE_SESSION_CACHE_NAME);
		sessionDao.setSessionIdGenerator(sessionIdGenerator());
		return sessionDao;
	}

	@Bean(name = "sessionIdGenerator")
	public JavaUuidSessionIdGenerator sessionIdGenerator() {
		JavaUuidSessionIdGenerator sessionIdGenerator = new JavaUuidSessionIdGenerator();
		return sessionIdGenerator;
	}
	
//	@Bean
//	public HashedCredentialsMatcher hashedCredentialsMatcher(){
//		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//		hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
//		hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
//		return hashedCredentialsMatcher;
//	}

	@Bean
	@DependsOn(value = "lifecycleBeanPostProcessor")
	public ShiroRealm userRealm() {
		ShiroRealm userRealm = new ShiroRealm();
		userRealm.setCacheManager(cacheManager());
//		userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return userRealm;
	}

	@Bean
	public EhCacheManager cacheManager() {
		EhCacheManager cacheManager = new EhCacheManager();
		cacheManager.setCacheManagerConfigFile(SiteConfig.EHCACHE_CONFIG);
		return cacheManager;
	}
	
	@Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor auth = new AuthorizationAttributeSourceAdvisor();
		auth.setSecurityManager(securityManager());
		return auth;
	}

	//配置shiro注解模式
    @Bean
    @DependsOn(value = "lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return aasa;
    }


}