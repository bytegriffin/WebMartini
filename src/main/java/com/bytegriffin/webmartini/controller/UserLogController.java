package com.bytegriffin.webmartini.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bytegriffin.webmartini.domain.UserLog;
import com.bytegriffin.webmartini.init.SiteConfig;
import com.bytegriffin.webmartini.interceptor.PermissionResource;
import com.bytegriffin.webmartini.service.UserLogService;
import com.bytegriffin.webmartini.util.DataTablesPage;
import com.bytegriffin.webmartini.util.DateUtil;
import com.bytegriffin.webmartini.util.StringHelper;
import com.github.pagehelper.PageHelper;

@Controller
public class UserLogController {

	@Autowired
	private UserLogService userLogService;

	@RequestMapping("/userlog/list")
	@RequiresPermissions(PermissionResource.USERLOG)
	//@ClickStream(value="用户浏览用户日志列表",  type=OperType.view)
	public String syslogList(HttpServletRequest request) {
		request.setAttribute("interval", SiteConfig.USER_LOG_INTERVAL);
		return "userlog/userlog_list";
	}

	@RequestMapping("/userlog/error/list")
	@RequiresPermissions(PermissionResource.USERLOG)
	//@ClickStream(value="用户浏览用户日志列表",  type=OperType.view)
	public String syslogErrorList(HttpServletRequest request) {
		request.setAttribute("interval_error", SiteConfig.USER_LOG_ERROR_INTERVAL);
		return "userlog/userlog_error_list";
	}

	@RequestMapping("/userlog/search")
	@ResponseBody
	@RequiresPermissions(PermissionResource.USERLOG)
	public DataTablesPage<UserLog> search(HttpServletRequest request) {
		return userlogPage(request);
	}

	@RequestMapping("/userlog/error/search")
	@ResponseBody
	@RequiresPermissions(PermissionResource.USERLOG)
	public DataTablesPage<UserLog> searchError(HttpServletRequest request) {
		return userlogErrorPage(request);
	}

	@RequestMapping("/userlog/page")
	@ResponseBody
	@RequiresPermissions(PermissionResource.USERLOG)
	public DataTablesPage<UserLog> userlogPage(HttpServletRequest request) {
		String operTime = request.getParameter("operTime");

		String searchValue = request.getParameter("searchValue");
		int start = StringHelper.isNullOrBlank(request.getParameter("start")) ? 0
				: Integer.valueOf(request.getParameter("start"));
		int pageSize = StringHelper.isNullOrBlank(request.getParameter("length")) ? 0
				: Integer.valueOf(request.getParameter("length"));
		int draw = StringHelper.isNullOrBlank(request.getParameter("draw")) ? 0
				: Integer.valueOf(request.getParameter("draw"));

		String orderColumn = request.getParameter("order[0][column]");
		String orderDir = request.getParameter("order[0][dir]");

		int pageNum = DataTablesPage.getPageNum(start, pageSize);
		PageHelper.startPage(pageNum, pageSize);
		if (!StringHelper.isNullOrBlank(orderColumn)) {
			if (orderColumn.equals("1")) {
				orderColumn = "oper_time";
			}else if (orderColumn.equals("2")) {
				orderColumn = "oper_time";
			}
			PageHelper.orderBy(orderColumn + " " + orderDir);
		}

		String[] dateRange = null;
		List<UserLog> list = null;
		try {
			if(StringHelper.isNullOrBlank(operTime)){
				list = userLogService.getUserLogs(searchValue, null ,null);
			} else {
				dateRange = DateUtil.getSearchDateRange(operTime);
				String startTime = dateRange[0];
				String endTime = dateRange[1];
				list = userLogService.getUserLogs(searchValue,startTime,endTime);
			}

			for(UserLog ul : list){
				ul.setOperTime( DateUtil.dateToStr(DateUtil.strToDate(ul.getOperTime())) );
			}

			DataTablesPage<UserLog> page = new DataTablesPage<UserLog>(list,draw,start,pageSize);
			return page;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping("/userlog/error/page")
	@ResponseBody
	@RequiresPermissions(PermissionResource.USERLOG)
	public DataTablesPage<UserLog> userlogErrorPage(HttpServletRequest request) {
		String operTime = request.getParameter("operTime");
		String searchValue = request.getParameter("searchValue");
		int start = StringHelper.isNullOrBlank(request.getParameter("start")) ? 0
				: Integer.valueOf(request.getParameter("start"));
		int pageSize = StringHelper.isNullOrBlank(request.getParameter("length")) ? 0
				: Integer.valueOf(request.getParameter("length"));
		int draw = StringHelper.isNullOrBlank(request.getParameter("draw")) ? 0
				: Integer.valueOf(request.getParameter("draw"));

		String orderColumn = request.getParameter("order[0][column]");
		String orderDir = request.getParameter("order[0][dir]");

		int pageNum = DataTablesPage.getPageNum(start, pageSize);
		PageHelper.startPage(pageNum, pageSize);
		if (!StringHelper.isNullOrBlank(orderColumn)) {
			if (orderColumn.equals("1")) {
				orderColumn = "oper_time";
			}else if (orderColumn.equals("2")) {
				orderColumn = "oper_time";
			}
			PageHelper.orderBy(orderColumn + " " + orderDir);
		}

		String[] dateRange = null;
		List<UserLog> list = null;
		try {
			if(StringHelper.isNullOrBlank(operTime)){
				list = userLogService.getErrorUserLogs(searchValue, null ,null);
			} else {
				dateRange = DateUtil.getSearchDateRange(operTime);
				String startTime = dateRange[0];
				String endTime = dateRange[1];
				list = userLogService.getErrorUserLogs(searchValue,startTime,endTime);
			}

			for(UserLog ul : list){
				if(StringHelper.isNullOrBlank(ul.getException()) && ul.getException().length() >= 50){
					ul.setException(ul.getException().substring(0, 50));
				}
				ul.setOperTime( DateUtil.dateToStr(DateUtil.strToDate(ul.getOperTime())) );
			}
			DataTablesPage<UserLog> page = new DataTablesPage<UserLog>(list,draw,start,pageSize);
			return page;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/userlog/suggest")
	@ResponseBody
	@RequiresPermissions(PermissionResource.USERLOG)
	//@ClickStream(value="用户输入关键字，准备搜索用户日志",  type=OperType.sugguest)
	public List<UserLog> suggest(HttpServletRequest request) {
		String searchValue = request.getParameter("searchValue");
		List<UserLog>	list = userLogService.suggest("user_name", searchValue);
		return list;
	}

	@RequestMapping("/userlog/error/suggest")
	@ResponseBody
	@RequiresPermissions(PermissionResource.USERLOG)
	//@ClickStream(value="用户输入关键字，准备搜索用户日志",  type=OperType.sugguest)
	public List<UserLog> errorSuggest(HttpServletRequest request) {
		String searchValue = request.getParameter("searchValue");
		List<UserLog>	list = userLogService.suggest("user_name", searchValue);
		return list;
	}

	@RequestMapping(value = "/userlog/error/view/{id}", method = RequestMethod.GET)
	@RequiresPermissions(PermissionResource.USERLOG)
	public String viewException(@PathVariable("id") String id,HttpServletRequest request) throws IOException {
		UserLog ul = userLogService.getUserLogById(id);
		request.setAttribute("userLog", ul);
		return "userlog/userlog_error_view";
	}

}
