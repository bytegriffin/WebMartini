package com.webmartini.init;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.webmartini.log.UserLogInterceptor;

//import com.webmartini.log.UserLogInterceptor;
@Configuration
//@EnableWebMvc //开启会覆盖默认目录，将静态资源定位于src/main/webapp，即SpringMVC的方式
public class SpringMvcConfig extends WebMvcConfigurerAdapter {

//    @Override
 //    public void configureDefaultServletHandling(
//            DefaultServletHandlerConfigurer configurer) {
//        configurer.enable();
//    }   

	/**
	 * addInterceptors执行之前必须事先声明Bean，
	 * 否则会使UserLogInterceptor中的@Autowired注入失败
	 * @return
	 */
	@Bean  
    public UserLogInterceptor userLogInterceptor() {  
        return new UserLogInterceptor();  
    }  

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(userLogInterceptor()).excludePathPatterns("/").excludePathPatterns("/index")
				.excludePathPatterns("/login").excludePathPatterns("/login/**").excludePathPatterns("/css/**").excludePathPatterns("/js/**")
				.excludePathPatterns("/images/**").excludePathPatterns("/fonts/**").excludePathPatterns("/upload/**").excludePathPatterns("/templates/**")
//				.excludePathPatterns("/layout/menus").excludePathPatterns("/layout/header").excludePathPatterns("/layout/footer")
				.excludePathPatterns("/user/message/count")
				.excludePathPatterns("/datatables/language/zh").addPathPatterns("/**");

		super.addInterceptors(registry);
	}

//    /**
//     * 修改DispatcherServlet默认配置
//     */
//    @Bean
//    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
//        ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
//        registration.getUrlMappings().clear();
//        registration.addUrlMappings("*.do");
//        registration.addUrlMappings("*.json");
//        return registration;
//    }

//	@Bean(name = "multipartResolver")
//	public CommonsMultipartResolver commonsMultipartResolver() {
//		CommonsMultipartResolver common = new CommonsMultipartResolver();
//		common.setDefaultEncoding("utf-8");
//		common.setMaxUploadSize(10 * 1024 * 1024);// 10M
//		common.setMaxInMemorySize(40960);
//		return common;
//	}

//	@Bean(name = "beetlConfig")
//	public BeetlGroupUtilConfiguration beetlConfig() {
//		BeetlGroupUtilConfiguration config = new BeetlGroupUtilConfiguration();
//		config.setConfigFileResource( new ClassPathResource("classpath:beetl.properties"));
//		return config;
//	}

//	@Bean(name = "beetlViewResolver")
//	public BeetlSpringViewResolver beetlViewResolver() {
//		BeetlSpringViewResolver view = new BeetlSpringViewResolver();
//		Properties mappings = new Properties();
//		mappings.put("prefix", "/resources/templates/");
//		mappings.put("suffix", ".html");
//		mappings.put("contentType", "text/html;charset=UTF-8");
//		mappings.put("order", "0");
//		mappings.put("config", beetlConfig());
//		view.setAttributes(mappings);
//		return view;
//	}

//	@Bean
//	public FilterRegistrationBean characterEncodingFilter() {
//		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
//		CharacterEncodingFilter filter = new CharacterEncodingFilter();
//		filter.setEncoding("UTF-8");
//		filter.setForceEncoding(true);
//		filterRegistration.setFilter(filter);
//		return filterRegistration;
//	}

//	@Bean
//	public FilterRegistrationBean customCharacterEncodingFilter() {
//		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
//		Filter filter = new CustomCharacterEncodingFilter();
//		filterRegistration.setFilter(filter);
//		return filterRegistration;
//	}

//	@Bean
//	@DependsOn(value = "lifecycleBeanPostProcessor")
//	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
//		DefaultAdvisorAutoProxyCreator daac = new DefaultAdvisorAutoProxyCreator();
//		daac.setProxyTargetClass(true);
//		return daac;
//	}



	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {    
	    return new EmbeddedServletContainerCustomizer(){       
	        @Override        
	         public void customize(ConfigurableEmbeddedServletContainer container) {            
	            container.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/error/400"));            
	            container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500"));            
	            container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"));        
	        }    
	    };
	}

}
