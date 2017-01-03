package com.mullet.backend.log;

import eu.bitwalker.useragentutils.UserAgent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import com.mullet.backend.entity.User;
import com.mullet.backend.entity.UserLog;
import com.mullet.backend.service.UserLogService;
import com.mullet.backend.util.Constant;
import com.mullet.backend.util.DateUtil;
import com.mullet.backend.util.IPHelper;
import com.mullet.backend.util.UUIDGenerator;
import com.mullet.backend.util.WebHelper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 记录用户点击流
 */
public class UserLogInterceptor implements HandlerInterceptor {

    // 本地异常日志记录对象
  private static final Logger logger = LoggerFactory.getLogger(UserLogInterceptor.class);

    // 注入Service用于把日志保存数据库
	@Resource
  private UserLogService logService;
	
	private String getContent(int status){
		String content = null;
		switch(status){
			case 404 :
				content = "您访问的页面不存在";
				break;
		}
		return content;
	}
	
	private void saveError(HttpServletRequest request, HttpServletResponse response){
		int httpStatus = response.getStatus();
		User user = WebHelper.getUserFromSession();
		String ip = IPHelper.getIpAddr(request);
		String uri = request.getRequestURI();
		String method = request.getMethod();
		String userName = request.getRemoteUser();
		String userAgent = request.getHeader(Constant.USER_AGENT);
		UserAgent ua = UserAgent.parseUserAgentString(userAgent);
		UserLog log = new UserLog();
		log.setId(UUIDGenerator.getUUID());
		log.setUserId(user.getId());
		log.setUserName(userName);
		log.setOperIp(ip);
		log.setOperTime(DateUtil.getCurrentDayTime());
		log.setException(uri + " " + getContent(httpStatus));
		log.setHttpStatus(httpStatus);
		log.setUrl(uri);
		log.setMethod(method);
		log.setOperContent(getContent(httpStatus));
		log.setBrowser(ua.getBrowser().getName() + " " + ua.getBrowserVersion());
		log.setOs(ua.getOperatingSystem().getName());
//		log.setController();
		log.setOperType(OperType.unknown.getValue());
		logService.insert(log);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception exception)
			throws Exception {
		try {
			int httpStatus = response.getStatus();
			if(httpStatus == 404){
				saveError(request,response);
				return;
			}
			User user = WebHelper.getUserFromSession();
			String ip = IPHelper.getIpAddr(request);
			String uri = request.getRequestURI();
			String method = request.getMethod();
			String userName = request.getRemoteUser();
			String userAgent = request.getHeader(Constant.USER_AGENT);
			UserAgent ua = UserAgent.parseUserAgentString(userAgent);
			if(obj instanceof DefaultServletHttpRequestHandler){
				return;
			}
			
			if(exception == null){
				Exception ex = (Exception)request.getAttribute("exception");
				exception = ex;
			}

			HandlerMethod hm = (HandlerMethod) obj;
			ClickStream sysControllerLog = hm.getMethodAnnotation(ClickStream.class);
			if(sysControllerLog == null && exception == null){
				return;
			}

			/* ==========数据库日志========= */
			UserLog log = new UserLog();
			log.setId(UUIDGenerator.getUUID());
			log.setUserId(user.getId());
			log.setUserName(userName);
			log.setOperIp(ip);
			log.setOperTime(DateUtil.getCurrentDayTime());
			if(exception != null){
				 StringWriter sw = new StringWriter();   
				 exception.printStackTrace(new PrintWriter(sw));
				 String message = sw.toString();
					log.setException(message);
			}
			log.setHttpStatus(httpStatus);
			log.setUrl(uri);
			log.setMethod(method);
			if(sysControllerLog != null){
				String content = sysControllerLog.value();
				String type = sysControllerLog.type().getValue();
				log.setOperContent(content);
				log.setOperType(type);
			} 

			log.setBrowser(ua.getBrowser().getName() +" " + ua.getBrowserVersion());
			log.setOs(ua.getOperatingSystem().getName());
			log.setController(obj.toString());

			logService.insert(log);
		} catch (Exception ex) {ex.printStackTrace();
			logger.error("异常信息:{}", ex.getMessage());
		} 
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView mav)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		return true;
	}

}