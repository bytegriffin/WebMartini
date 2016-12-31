package com.webmartini.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.subject.Subject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.webmartini.domain.Menu;
import com.webmartini.domain.Message;
import com.webmartini.domain.User;
import com.webmartini.domain.UserLog;
import com.webmartini.domain.UserMessage;
import com.webmartini.init.SiteConfig;
import com.webmartini.interceptor.ShiroRealm;
import com.webmartini.log.ClickStream;
import com.webmartini.log.OperType;
import com.webmartini.service.MenuService;
import com.webmartini.service.MessageService;
import com.webmartini.service.UserLogService;
import com.webmartini.service.UserMessageService;
import com.webmartini.service.UserService;
import com.webmartini.util.CipherUtil;
import com.webmartini.util.DateUtil;
import com.webmartini.util.FileHelper;
import com.webmartini.util.IPHelper;
import com.webmartini.util.StringHelper;
import com.webmartini.util.UUIDGenerator;
import com.webmartini.util.VerifyCodeUtil;
import com.webmartini.util.WebHelper;

import eu.bitwalker.useragentutils.UserAgent;

@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	private static final String SESSION_LOGIN_VERIFYCODE = "login_VerifyCode";// 登录图片验证码
	private static final String SESSION_FORGET_VERIFYCODE = "forget_VerifyCode";// 忘记密码图片验证码

	@Autowired
	private UserService userService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private ShiroRealm shiroRealm;

	@Autowired
	private UserLogService logService;

	@Autowired
	private UserMessageService userMsgService;

	@Autowired
	private MessageService msgService;

	@RequestMapping(value={"","/","index"})
    public String index() {
        return "index";
    }

	@RequestMapping(value = "/login/validate/email", method = RequestMethod.POST)
	@ResponseBody
	public String validateEmail(HttpServletRequest request) {
		String email = request.getParameter("email");
		User user = userService.getUserByLoginEmail(email);
		if (user == null) {
			request.setAttribute("forget_message", "Email输入错误，请重新输入");
			return "{\"valid\": false}";
		}
		return "{\"valid\": true}";
	}

	@RequestMapping("/main")
	@RequiresPermissions(SiteConfig.SHIRO_PERMISSION_COMMON)
	@ClickStream(value = "用户已成功登入系统", type = OperType.login)
	public String main(HttpServletRequest request,HttpServletResponse response) {
		WebHelper.removeLoginSession();// 将登录页面的图片验证码信息全部清空
		User currentUser = WebHelper.getUserFromSession();
		request.setAttribute("user", currentUser);
		request.setAttribute("site_name", SiteConfig.SITE_NAME);

		//加载menu tree
		List<Menu> list = menuService.getEnabledMenus(currentUser.getId());
		String parentid = "0";
		List<Menu> newList = travelTree(list, parentid);
		request.setAttribute("menus", newList);

		//加载header
		if(!StringHelper.isNullOrBlank(currentUser.getAvatar())){
			String path = request.getSession().getServletContext().getRealPath("/");
			File avatar = FileUtils.getFile(path + currentUser.getAvatar());
			if (!avatar.exists()) {
				currentUser.setAvatar(null);
			}
		}

		List<String> groupids = userService.getGroupIdsByUserId(currentUser.getId());
		List<String> roleids = userService.getRoleIdsByUserId(currentUser.getId());
		List<UserMessage> umlist = userMsgService.getAllUserMessagesByRecId(currentUser.getId());
		List<String> uml = new ArrayList<String>();
		for(UserMessage um : umlist){
			uml.add(um.getMsgId());
		}

		List<Message> mlist = msgService.getMessages(Message.SEND_STATUS);
		for(Message msg : mlist){
			if (uml.size() > 0 && uml.contains(msg.getId())) {
				continue;
			} else {
				if((!StringHelper.isNullOrBlank(msg.getRecId()) && Message.ALL_USER_RECID.equals(msg.getRecId()))) {
					UserMessage um = new UserMessage();
					um.setMsgId(msg.getId());
					um.setRecId(currentUser.getId());
					um.setSendId(msg.getSendId());
					um.setSendTime(msg.getSendTime());
					um.setStatus(UserMessage.UNREAD_STATUS);
					userMsgService.insert(um);
					logger.info("收取一条sendid为["+msg.getSendId()+"]发给全体用户的站内信.");
				} else if ( !StringHelper.isNullOrBlank(msg.getRoleIds()) ) {
					List<String> msgrolelist = StringHelper.StringToList(msg.getRoleIds());
					for (String roleid : msgrolelist) {
						if(roleids.contains(roleid)){
							UserMessage um = new UserMessage();
							um.setMsgId(msg.getId());
							um.setRecId(currentUser.getId());
							um.setSendId(msg.getSendId());
							um.setSendTime(msg.getSendTime());
							um.setStatus(UserMessage.UNREAD_STATUS);
							userMsgService.insert(um);
							logger.info("收取一条sendid为["+msg.getSendId()+"]发给角色id为["+roleid+"]的站内信.");
							break;
						}
					}
				} else if (!StringHelper.isNullOrBlank(msg.getGroupIds())){
					List<String> msggrouplist = StringHelper.StringToList(msg.getGroupIds());
					for (String groupid : msggrouplist) {
						if(groupids.contains(groupid)){
							UserMessage um = new UserMessage();
							um.setMsgId(msg.getId());
							um.setRecId(currentUser.getId());
							um.setSendId(msg.getSendId());
							um.setSendTime(msg.getSendTime());
							um.setStatus(UserMessage.UNREAD_STATUS);
							userMsgService.insert(um);
							logger.info("收取一条sendid为["+msg.getSendId()+"]发给组织id为["+groupid+"]的站内信.");
							break;
						}
					}
				}
			}
		}

		//获取3条系统站内信
		List<UserMessage> newumlist = userMsgService.getTopUserMessagesByRecId(currentUser.getId());
		for(UserMessage um : newumlist){
			um.setSendTime(DateUtil.dateToStr(DateUtil.strToDate(um.getSendTime())));
			if(um.getContent().length() > 20){
				um.setContent(um.getContent().substring(0, 20)+"......");
			}
			if(!FileHelper.exist(um.getAvatar())){
				um.setAvatar(null);
			}
		}
		request.setAttribute("UserMessageList", newumlist);
		return "main";
	}

	/**
	 * 迭代菜单
	 */
	private List<Menu> travelTree(List<Menu> list, String parentId) {
		List<Menu> newList = new ArrayList<Menu>();
		for (Menu m : list) {
			String id = m.getId();
			String pid = m.getParentId();
			if (parentId.equals(pid)) {
				List<Menu> sublist = travelTree(list, id);
				m.setChildren(sublist);
				newList.add(m);
			}
		}
		return newList;
	}

	/**
	 * 获取验证码图片和文本(验证码文本会保存在HttpSession中)
	 */
	@RequestMapping("/login/getVerifyCodeImage")
	public void getVerifyCodeImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 设置页面不缓存
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		String flag = request.getParameter("flag");
		String sessionkey = SESSION_LOGIN_VERIFYCODE;
		if (flag.equals("login")) {
			sessionkey = SESSION_LOGIN_VERIFYCODE;
		} else if (flag.equals("forget")) {
			sessionkey = SESSION_FORGET_VERIFYCODE;
		}
		String verifyCode = VerifyCodeUtil.generateTextCode(VerifyCodeUtil.TYPE_NUM_LOWER, 4, null);
		// 将验证码放到HttpSession里面
		WebHelper.setSession(sessionkey, verifyCode);
		// 设置输出的内容的类型为JPEG图像
		response.setContentType("image/jpeg");
		BufferedImage bufferedImage = VerifyCodeUtil.generateImageCode(verifyCode, 90, 30, 3, true, Color.WHITE,
				Color.BLACK, null);
		// 写给浏览器
		ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());
	}

	@RequestMapping(value = "/login/validate/verifycode", method = RequestMethod.POST)
	@ResponseBody
	public String validateVerifyCode(HttpServletRequest request) {
		String flag = request.getParameter("flag");
		String requestVerifyCode = "";
		String sessionkey = SESSION_LOGIN_VERIFYCODE;
		if (flag.equals("login")) {
			requestVerifyCode = request.getParameter("verifycode");
			sessionkey = SESSION_LOGIN_VERIFYCODE;
		} else if (flag.equals("forget")) {
			requestVerifyCode = request.getParameter("forgetverifycode");
			sessionkey = SESSION_FORGET_VERIFYCODE;
		}
		String storeVerifyCode = (String) WebHelper.getSession(sessionkey);
		if (requestVerifyCode.equals(storeVerifyCode)) {
			return "{\"valid\": true}";
		}
		return "{\"valid\": false}";
	}

	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String tologin(HttpServletRequest request, HttpServletResponse response) {
		Subject currentUser = SecurityUtils.getSubject();
		String username = (String) currentUser.getPrincipal();
		request.setAttribute("site_name", SiteConfig.SITE_NAME);

		// 是否是在session未失效或者cache未失效的情况下
		if (currentUser.isAuthenticated()) {
			return "redirect:/main";
		} else if (currentUser.isRemembered()) {// 是否在session过期，但缓存未过期
			// 从cache中加载认证信息到session
			Cache<Object, AuthenticationInfo> authenticationCache = shiroRealm.getAuthenticationCache();
			AuthenticationInfo auth = authenticationCache.get(username);
			String password = (String) auth.getCredentials();

			User user = userService.getUserByUserName(username);
			if (user == null) {
				request.setAttribute("message", "当前账号：" + username + "已不存在，请联系系统管理员");
				return "login";
			}
			if (!CipherUtil.validatePassword(user.getPassword(), password)) {
				request.setAttribute("message", "当前账号密码已被修改，请联系系统管理员");
				return "login";
			}
			if (user.getStatus().equals(User.LOCK_STATUS)) {
				request.setAttribute("message", "此账号已被锁定，请联系系统管理员");
				return "login";
			}
			WebHelper.setUserSession(user);

			shiroRealm.updateAuthorizationCache(username);
			//更新用户登陆状态
			userService.updateUserLoginStatus(user.getId(), IPHelper.getIpAddr(request), user.getLoginCount());
			
			return "redirect:/main";
		}
		// session 过期，并且没有点击 ”记住我“ 选项
		return "login";
	}

	/**
	 * 登录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ClickStream(value = "用户正在登录系统...", type = OperType.login)
	public String login(HttpServletRequest request) throws AuthenticationException {
		String type = request.getParameter("type");
		if (!StringHelper.isNullOrBlank(type)) {
			String email = request.getParameter("email");
			User user = userService.getUserByLoginEmail(email);
			// 因为index页面会发生跳转，因此用session来存储属性
			if (user == null) {
				request.setAttribute("forget_message", "邮箱输入错误，请重新输入");
			} else {
				// 发送邮件 。。。。。
				request.setAttribute("forget_message", "发送成功，请登录邮箱获取账户密码");
			}
			request.setAttribute("email", email);
			return "login";
		}

		String username = request.getParameter("username");
		// MD5加密
		// String password =
		// CipherUtil.generatePassword(request.getParameter("password"));
		String password = request.getParameter("password");
		String remember = request.getParameter("rememberMe");

		UsernamePasswordToken token = new UsernamePasswordToken(username, CipherUtil.generatePassword(password));
		Subject currentUser = SecurityUtils.getSubject();
		try {
			if ("on".equals(remember)) {
				token.setRememberMe(true);
			} else {
				token.setRememberMe(false);
			}

			// 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
			// 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
			// 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
			logger.info("对用户[" + username + "]进行登录验证..验证开始");
			currentUser.login(token);
			User user = userService.getUserByUserName(username);
			WebHelper.setUserSession(user);
			shiroRealm.removePasswordRetryCache(username);// 如果登录成功，就把密码输入次数的缓存清空
			logger.info("对用户[" + username + "]进行登录验证..验证通过");
			//更新用户登陆状态
			userService.updateUserLoginStatus(user.getId(), IPHelper.getIpAddr(request), user.getLoginCount());

		} catch (UnknownAccountException uae) {
			request.setAttribute("username", username);
			request.setAttribute("password", password);
			request.setAttribute("message", "账号或密码错误");
			return "login";
		} catch (IncorrectCredentialsException ice) {
			request.setAttribute("username", username);
			request.setAttribute("password", password);
			request.setAttribute("message", "账号或密码错误");
			return "login";
		} catch (LockedAccountException lae) {
			request.setAttribute("username", username);
			request.setAttribute("password", password);
			request.setAttribute("message", "此账号已被锁定");
			return "login";
		} catch (ExcessiveAttemptsException eae) {
			request.setAttribute("username", username);
			request.setAttribute("password", password);
			request.setAttribute("message", "账号或密码错误次数过多，请在十分钟后重新登录");
			return "login";
		} catch (UnauthorizedException e) {
			request.setAttribute("username", username);
			request.setAttribute("password", password);
			request.setAttribute("message", "此账号没有被授权");
			return "login";
		}
		return "redirect:/main";
	}

	/**
	 * 记录用户退出日志，因为退出会删掉session，不能通过    <br />
	 * UserLogInterceptor记录， 所以手动写一个方法记录了。 <br />
	 * @param request
	 * @param exception
	 */
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		UserLog log = null;
		try {
			User user = WebHelper.getUserFromSession();
			String ip = IPHelper.getIpAddr(request);
			String uri = request.getRequestURI();
			String method = request.getMethod();
			String userName = request.getRemoteUser();
			String userAgent = request.getHeader(SiteConfig.USER_AGENT);
			int httpStatus = response.getStatus();
			UserAgent ua = UserAgent.parseUserAgentString(userAgent);

			/* ==========数据库日志========= */
			log = new UserLog();
			log.setId(UUIDGenerator.getUUID());
			log.setUserId(user.getId());
			log.setUserName(userName);
			log.setOperIp(ip);
			log.setOperTime(DateUtil.getCurrentDayTime());
			log.setUrl(uri);
			log.setMethod(method);
			log.setOperContent("用户已登出系统");
			log.setBrowser(ua.getBrowser().getName() + " " + ua.getBrowserVersion());
			log.setOs(ua.getOperatingSystem().getName());
			log.setController("public String logout(HttpServletRequest request)");
			log.setOperType(OperType.logout.getValue());
			log.setHttpStatus(httpStatus);

			// 先记住用户名称再调用退出命令
			Subject currentUser = SecurityUtils.getSubject();
			currentUser.logout();
			shiroRealm.clearAllCached();
		} catch (Exception exception) {
			StringWriter sw = new StringWriter();
			exception.printStackTrace(new PrintWriter(sw));
			String message = sw.toString();
			log.setException(message);

			logger.error("异常信息:{}", exception.getMessage());
		} finally {
			logService.insert(log);
		}
		return "redirect:index.html";
	}

}
