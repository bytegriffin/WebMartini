package com.bytegriffin.webmartini.init;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;


@Configuration
public class SiteConfig {

	public static String MYBATIS_PACKAGES;
	public static String MYBATIS_MAPPER_LOCATION;
	public static String EHCACHE_CONFIG;

	public static Long SHIRO_GLOBAL_SESSION_TIMEOUT;
	public static Integer SHIRO_COOKIE_MAX_AGE;
	public static Integer SHIRO_REMEMBERME_COOKIE_MAX_AGE;
	public static String SHIRO_ACTIVE_SESSION_CACHE_NAME;
	public static String SHIRO_PASSWORD_RETRY_CACHE;
	public static String SHIRO_CREDENTIALS_SALT;
	public final static String SHIRO_PERMISSION_COMMON = "main";// 访问控制权限，默认全部用户共同拥有

	//用户日志页面自动刷新时间间隔：毫秒单位
	public static String USER_LOG_INTERVAL;
	//系统异常页面自动刷新时间间隔：毫秒单位
	public static String USER_LOG_ERROR_INTERVAL;

	public static final String USER_AGENT = "User-Agent";

	//用户头像上传路径
	public static String AVATAR_PATH;
	//系统公告附件地址
	public static String ATTACHMENT_PATH;
	//系统名称包括(title/footer页的命名)
	public static String SITE_NAME;

   /**
	 * 多个配置类存在的情况下，只能把这个方法当成入口，
	 * @param env
	 * @return
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer(Environment env) {
		PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
		MYBATIS_PACKAGES = env.getProperty("mybatis.type-aliases-package", "com.webmartini.domain");
		MYBATIS_MAPPER_LOCATION = env.getProperty("mybatis.mapper-locations", "classpath*:/mybatis/*Mapper.xml");
		EHCACHE_CONFIG = env.getProperty("spring.cache.ehcache.config", "classpath:ehcache.xml");
		
		SHIRO_CREDENTIALS_SALT = env.getProperty("site.shiro.credentials-salt", "webmartini");
		SHIRO_GLOBAL_SESSION_TIMEOUT = Long.valueOf(env.getProperty("site.shiro.global-session-timeout", "1800000L"));
		SHIRO_COOKIE_MAX_AGE = Integer.valueOf(env.getProperty("site.shiro.cookie-max-age", "180000"));
		SHIRO_REMEMBERME_COOKIE_MAX_AGE = Integer.valueOf(env.getProperty("site.shiro.rememberme-cookie-max-age", "864000"));
		SHIRO_ACTIVE_SESSION_CACHE_NAME = env.getProperty("site.shiro.active-sessions-cache-name", "shiro-activeSessionCache");
		SHIRO_PASSWORD_RETRY_CACHE = env.getProperty("site.shiro.password-retry-cache", "passwordRetryCache");
		
		USER_LOG_INTERVAL = env.getProperty("site.userlog.interval", "10000");
		USER_LOG_ERROR_INTERVAL = env.getProperty("site.userlog.error.interval", "60000");

		AVATAR_PATH = env.getProperty("site.avatar-path", "/upload/avatar/");
		ATTACHMENT_PATH = env.getProperty("site.attachment-path", "/upload/attachment/");
		
		SITE_NAME = env.getProperty("site.name", "webmartini");
		
		return pspc;
	}

}
