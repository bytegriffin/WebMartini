package com.webmartini.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.webmartini.chart.chartjs.structure.ChartjsData;
import com.webmartini.chart.chartjs.structure.DataSet;
import com.webmartini.domain.Group;
import com.webmartini.domain.Role;
import com.webmartini.domain.User;
import com.webmartini.domain.UserLog;
import com.webmartini.domain.UserRef;
import com.webmartini.init.SiteConfig;
import com.webmartini.interceptor.PermissionResource;
import com.webmartini.interceptor.ShiroRealm;
import com.webmartini.log.ClickStream;
import com.webmartini.log.OperType;
import com.webmartini.service.GroupService;
import com.webmartini.service.RoleService;
import com.webmartini.service.UserLogService;
import com.webmartini.service.UserMessageService;
import com.webmartini.service.UserService;
import com.webmartini.util.CipherUtil;
import com.webmartini.util.DataTablesPage;
import com.webmartini.util.DateUtil;
import com.webmartini.util.ExcelUtil;
import com.webmartini.util.PdfUtil;
import com.webmartini.util.PrintUtil;
import com.webmartini.util.StringHelper;
import com.webmartini.util.WebHelper;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private ShiroRealm shiroRealm;
	@Autowired
	private UserMessageService userMsgService;
	@Autowired
	private UserLogService userLogService;
	
	@RequestMapping("/user/dashboard")
	public String dashboard(HttpServletRequest request) throws IOException {

		return "user/user_dashboard";
	}

    /**
	 * 目前还存在一个格式问题，就是当分页时，有可能会出现半个cell被分割的情况，需要调整高度
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/print", produces = "application/text; charset=utf-8")
	@RequiresPermissions(PermissionResource.USER)
	@ResponseBody
	@ClickStream(value="打印用户列表",  type=OperType.print)
	public String print(HttpServletRequest request, HttpServletResponse response) {
		// 获取页面用户搜索记录
		String selectRange = request.getParameter("selectRange");
		String searchValue = request.getParameter("searchValue");
		if (!StringHelper.isNullOrBlank(searchValue)) {
			if ("loginName".equals(selectRange)) {
				selectRange = "login_name";
			}
		}
		List<User> list = userService.getUsers(selectRange, searchValue);

		String title = "用户列表";
		String[] head = { "登陆名称", "Email", "手机号码", "真实姓名", "性别", "出生日期", "身份证号" };
		List<String[]> exceldata = new ArrayList<String[]>();
		for (User user : list) {
			String[] data = new String[head.length];
			data[0] = user.getLoginName();
			data[1] = user.getEmail();
			data[2] = user.getPhone();
			data[3] = StringHelper.isNullOrBlank(user.getName()) ? "" : user.getName();
			data[4] = user.getSexStr(user.getSex());
			data[5] = StringHelper.isNullOrBlank(user.getBirthday()) ? "" : user.getBirthday();
			data[6] = StringHelper.isNullOrBlank(user.getIdNumber()) ? "" : user.getIdNumber();
			exceldata.add(data);
		}

		String str = PrintUtil.print(response, title, head, exceldata);
		return str;
	}

	@RequestMapping("/user/export/pdf")
	@RequiresPermissions(PermissionResource.USER)
	@ClickStream(value="导出pdf格式的用户列表",  type=OperType.exp_pdf)
	public void exportpdf(HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException, IOException {
		// 获取页面用户搜索记录
		String selectRange = request.getParameter("selectRange");
		String searchValue = request.getParameter("searchValue");
		if (!StringHelper.isNullOrBlank(searchValue)) {
			if ("loginName".equals(selectRange)) {
				selectRange = "login_name";
			}
		}
		List<User> list = userService.getUsers(selectRange, searchValue);

		String title = "用户列表";
		String[] head = { "登陆名称", "Email", "手机号码", "真实姓名", "性别", "出生日期", "身份证号" };
		List<String[]> exceldata = new ArrayList<String[]>();
		for (User user : list) {
			String[] data = new String[head.length];
			data[0] = user.getLoginName();
			data[1] = user.getEmail();
			data[2] = user.getPhone();
			data[3] = StringHelper.isNullOrBlank(user.getName()) ? "" : user.getName();
			data[4] = user.getSexStr(user.getSex());
			data[5] = StringHelper.isNullOrBlank(user.getBirthday()) ? "" : user.getBirthday();
			data[6] = StringHelper.isNullOrBlank(user.getIdNumber()) ? "" : user.getIdNumber();
			exceldata.add(data);
		}

		PdfUtil.exportPDF(response, "用户列表.pdf", title, head, exceldata);
	}

	@RequestMapping(value = "/user/import/excel", method = RequestMethod.GET)
	@RequiresPermissions(PermissionResource.USER)
	@ClickStream(value="用户正在浏览批量导入用户数据页面",  type=OperType.view)
	public String importxls(HttpServletRequest request) {
		return "user/user_import";
	}

	@RequestMapping(value = "/user/import/excel", method = RequestMethod.POST)
	@RequiresPermissions(PermissionResource.USER)
	@ResponseBody
	@ClickStream(value="用户正在批量导入excel格式的用户数据...",  type=OperType.import_excel)
	public String importxls(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
		try {
			if (file.isEmpty()) {
				return "{\"status\": false,   \"message\": \"导入的excel文件为空，请检查后重新导入\"}";
			}
			List<User> userlist = new ArrayList<User>();
			List<Map<Integer, String>> exceldata = ExcelUtil.readExcel(file.getInputStream());
			if(exceldata.size() == 0){
				return "{\"status\": false,   \"message\": \"导入的excel文件内容为空，请按照模板填写内容后重新导入\"}";
			}
			for (Map<Integer, String> map : exceldata) {
				User user = new User();
				user.setLoginName(map.get(0));
				user.setPassword(CipherUtil.generatePassword(map.get(1)));
				user.setEmail(map.get(2));
				user.setPhone(map.get(3));
				user.setName(map.get(4));
				user.setSex(map.get(5));
				user.setBirthday(map.get(6));
				user.setIdNumber(map.get(7));
				user.setStatus(User.ACTIVE_STATUS);
				userlist.add(user);
			}
			userService.insertUsers(userlist);
		} catch (Exception e) {
			return "{\"status\": false,   \"message\": \"" + e.getMessage().replaceAll("\"", "") + "\"}";
		}
		return "{\"status\": true,   \"message\": \"导入成功\"}";
	}

	@RequestMapping("/user/export/excel")
	@RequiresPermissions(PermissionResource.USER)
	@ClickStream(value="导出excel格式的用户列表",  type=OperType.exp_excel)
	public void exportxls(HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException, IOException {
		// 获取页面用户搜索记录
		String selectRange = request.getParameter("selectRange");
		String searchValue = request.getParameter("searchValue");
		if (!StringHelper.isNullOrBlank(searchValue)) {
			if ("loginName".equals(selectRange)) {
				selectRange = "login_name";
			}
		}
		List<User> list = userService.getUsers(selectRange, searchValue);

		// 拼凑Excel数据
		String sheetName = "用户列表";
		String[] titles = { "登陆名称", "Email", "手机号码", "真实姓名", "性别", "出生日期", "身份证号" };
		List<String[]> exceldata = new ArrayList<String[]>();
		for (User user : list) {
			String[] data = new String[titles.length];
			data[0] = user.getLoginName();
			data[1] = user.getEmail();
			data[2] = user.getPhone();
			data[3] = StringHelper.isNullOrBlank(user.getName()) ? "" : user.getName();
			data[4] = user.getSexStr(user.getSex());
			data[5] = StringHelper.isNullOrBlank(user.getBirthday()) ? "" : user.getBirthday();
			data[6] = StringHelper.isNullOrBlank(user.getIdNumber()) ? "" : user.getIdNumber();
			exceldata.add(data);
		}

		// 输出Excel
		ExcelUtil.writeToFile(response, "用户列表.xlsx", sheetName, titles, exceldata);
	}

	@RequestMapping(value = "/user/avatar", method = RequestMethod.GET)
	@ClickStream(value="打开用户头像页面",  type=OperType.view)
	public String openAvatar(HttpServletRequest request) {
		User user = WebHelper.getUserFromSession();
		request.setAttribute("user", user);
		return "user/user_avatar";
	}

	@RequestMapping(value = "/user/upload/avatar", method = RequestMethod.POST)
	@ResponseBody
	@ClickStream(value="用户上传头像图片",  type=OperType.upload)
	public String uploadAvatar(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
		User user = WebHelper.getUserFromSession();
		String path = request.getSession().getServletContext().getRealPath("/");
		String fileName = SiteConfig.AVATAR_PATH + user.getId() + "/" +file.getOriginalFilename();
		try {
			if (file.isEmpty()) {
				return "{\"status\": false,   \"message\": \"上传的图片文件为空，请检查后重新上传\"}";
			}
			File serverfile = new File(path + fileName);
			FileUtils.copyInputStreamToFile(file.getInputStream(), serverfile);
		} catch (Exception e) {
			return "{\"status\": false,   \"message\": \"上传失败\"}";
		}

		userService.updateUserAvatar(user.getId(), fileName);
		User currentUser = userService.getUserById(user.getId());
		WebHelper.setUserSession(currentUser);
		return "{\"status\": true, \"message\": \"上传成功\",    \"avatar\": \"" + currentUser.getAvatar() + "\"}";
	}

	@RequestMapping("/user/profile")
	@ClickStream(value="用户浏览个人设置页面",  type=OperType.view)
	public String profile(HttpServletRequest request) {
		User user = WebHelper.getUserFromSession();
		// 每次刷新都要重新获取，而不是一直从session取
		User currentUser = userService.getUserById(user.getId());
		List<String> roles = userService.getRoleNamesByUserId(user.getId());
		List<String> groups = userService.getGroupNamesByUserId(user.getId());
		currentUser.setRoleNamesStr(StringHelper.ListToString(roles));
		currentUser.setGroupNamesStr(StringHelper.ListToString(groups));
		if(!StringHelper.isNullOrBlank(currentUser.getAvatar())){
			String path = request.getSession().getServletContext().getRealPath("/");
			File avatar = FileUtils.getFile(path + currentUser.getAvatar());
			if (!avatar.exists()) {
				currentUser.setAvatar(null);
			}
		}
		WebHelper.setUserSession(currentUser);

		List<UserLog> list = userLogService.getRecentImportantEvent(user.getId());

		int count = userMsgService.getUnReadCountByRecId(user.getId());
		request.setAttribute("msgcount", count);
		request.setAttribute("user", currentUser);
		request.setAttribute("ullist", list);
		//计算当前登陆用的资料完整度
		int progress_count=0;
    	if(!StringHelper.isNullOrBlank(currentUser.getName())){
    		progress_count = progress_count + 20;
    	} 
    	if(!StringHelper.isNullOrBlank(currentUser.getSex())){
    		progress_count = progress_count + 20;
    	} 
    	if(!StringHelper.isNullOrBlank(currentUser.getBirthday())){
    		progress_count = progress_count + 20;
    	} 
    	if(!StringHelper.isNullOrBlank(currentUser.getPhone())){
    		progress_count = progress_count + 20;
    	} 
    	if(!StringHelper.isNullOrBlank(currentUser.getIdNumber())){
    		progress_count = progress_count + 20;
    	}
    	request.setAttribute("progress_count", progress_count);
		return "user/user_profile";
	}

	@RequestMapping("/user/chart/pv")
	@ResponseBody
	public ChartjsData getPVByUserId(HttpServletRequest request) {
		String userId = WebHelper.getUserIdFromSession();
		List<UserLog> ullist = userLogService.getPVByUserId(userId);
		ChartjsData chart = new ChartjsData();
		String[] labels = new String[ullist.size()];
		for(int i=0; i<ullist.size(); i++){
			UserLog ul = ullist.get(i);
			labels[i] = DateUtil.formatDate(ul.getDays());
		}
		chart.setLabels(labels);
		List<DataSet> list = new ArrayList<DataSet>();
		DataSet ds1 = new DataSet();
		ds1.setLabel("PV值");
		String[] data1 = {"51", "30"};
		ds1.setData(data1);
		list.add(ds1);
		chart.setDatasets(list);
		return chart;
	}

	@RequestMapping(value = "/user/edit/profile", method = RequestMethod.POST)
	@ResponseBody
	@ClickStream(value="用户修改个人详细资料",  type=OperType.edit)
	public String profileEdit(HttpServletRequest request) {
		String id = WebHelper.getUserIdFromSession();
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String idNumber = request.getParameter("idNumber");
		String birthday = request.getParameter("birthday");
		String phone = request.getParameter("phone");
		User user = new User();
		user.setId(id);
		user.setName(name);
		user.setIdNumber(idNumber);
		user.setSex(sex);
		user.setPhone(phone);
		user.setBirthday(birthday);
		userService.editUserProfile(user);
		return "true";
	}

	@RequestMapping(value = "/user/edit/password", method = RequestMethod.POST)
	@ResponseBody
	@ClickStream(value="用户修改个人登录密码",  type=OperType.edit)
	public String passwordEdit(HttpServletRequest request) {
		String userId = WebHelper.getUserIdFromSession();
		String password = CipherUtil.generatePassword(request.getParameter("password"));
		userService.editUserPassword(userId, password);

		shiroRealm.updateAuthenticationCache(password);

		return "true";
	}

	@RequestMapping("/user/validate/password")
	@ResponseBody
	public String validatePassword(HttpServletRequest request) {
		String userId = WebHelper.getUserIdFromSession();
		String currentpassword = request.getParameter("currentpassword");
		User user = userService.getUserById(userId);
		if (CipherUtil.validatePassword(user.getPassword(), currentpassword)) {
			return "{\"valid\": true}";
		}
		return "{\"valid\": false}";
	}

	@RequestMapping("/user/suggest")
	@ResponseBody
	@RequiresPermissions(PermissionResource.USER)
	@ClickStream(value="输入关键词，准备搜索",  type=OperType.sugguest)
	public List<User> suggest(HttpServletRequest request) {
		List<User> userList = null;
		String selectRange = request.getParameter("selectRange");
		String searchValue = request.getParameter("searchValue");
		if ("loginName".equals(selectRange)) {
			userList = userService.suggest("login_name", searchValue);
			for (User u : userList) {
				u.setName(u.getLoginName());
			}
		} else if ("email".equals(selectRange)) {
			userList = userService.suggest("email", searchValue);
			for (User u : userList) {
				u.setName(u.getEmail());
			}
		} else if ("phone".equals(selectRange)) {
			userList = userService.suggest("phone", searchValue);
			for (User u : userList) {
				u.setName(u.getPhone());
			}
		}
		return userList;
	}

	@RequestMapping(value = "/user/edit/{id}", method = RequestMethod.GET)
	@RequiresPermissions(PermissionResource.USER)
	@ClickStream(value="用户浏览修改页面",  type=OperType.view)
	public String openEditUser(@PathVariable("id") String id, Model model) {
		User user = userService.getUserById(id);
		List<String> groupids = userService.getGroupIdsByUserId(id);
		List<String> roleids = userService.getRoleIdsByUserId(id);
		List<Role> list = roleService.getRoles(null);
		List<Group> glist = groupService.getGroups(null);
		for (Role r : list) {
			if (roleids.contains(r.getId())) {
				r.setSelected(true);
			}
		}
		for (Group g : glist) {
			if (groupids.contains(g.getId())) {
				g.setSelected(true);
			}
		}
		model.addAttribute("roles", list);
		model.addAttribute("groups", glist);
		model.addAttribute("user", user);
		return "user/user_edit";
	}

	@RequestMapping(value = "/user/edit", method = RequestMethod.POST)
	@RequiresPermissions(PermissionResource.USER)
	@ClickStream(value="修改某用户信息",  type=OperType.edit)
	public @ResponseBody String editUser(HttpServletRequest request) {
		String id = request.getParameter("id");
		String loginName = request.getParameter("loginName");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String[] roleids = request.getParameterValues("role");
		String[] groupids = request.getParameterValues("group");
		User user = new User();
		user.setId(id);
		user.setLoginName(loginName);
		user.setEmail(email);
		user.setPassword(CipherUtil.generatePassword(password));
		user.setPhone(phone);
		userService.editUser(user);
		userService.delUserRoleByUserId(id);
		if (roleids != null) {
			List<UserRef> strList = StringHelper.StringToUserRefList(id, roleids);
			userService.insertUserRole(strList);
		}
		userService.delGroupUserByUserId(id);
		if (groupids != null) {
			List<UserRef> strList = StringHelper.StringToUserRefList(id, groupids);
			userService.insertGroupUser(strList);
		}
		return "true";
	}

	@RequestMapping(value = "/user/add", method = RequestMethod.GET)
	@RequiresPermissions(PermissionResource.USER)
	@ClickStream(value="用户浏览新增用户页面",  type=OperType.view)
	public String openAddUser(HttpServletRequest request) {
		List<Role> list = roleService.getRoles(null);
		List<Group> glist = groupService.getGroups(null);
		request.setAttribute("roles", list);
		request.setAttribute("groups", glist);
		return "user/user_add";
	}

	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	@RequiresPermissions(PermissionResource.USER)
	@ClickStream(value="新增一个用户",  type=OperType.add)
	public @ResponseBody String addUser(HttpServletRequest request) {
		String[] roleids = request.getParameterValues("role");
		String[] groupids = request.getParameterValues("group");
		String loginName = request.getParameter("loginName");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		User user = new User();
		user.setLoginName(loginName);
		user.setEmail(email);
		user.setPassword(CipherUtil.generatePassword(password));
		user.setPhone(phone);
		user.setStatus(User.ACTIVE_STATUS);
		userService.insertUser(user);
		String userid = user.getId();
		if (roleids != null) {
			List<UserRef> list = StringHelper.StringToUserRefList(userid, roleids);
			userService.insertUserRole(list);
		}
		if (groupids != null) {
			List<UserRef> list = StringHelper.StringToUserRefList(userid, groupids);
			userService.insertGroupUser(list);
		}
		return "true";
	}

	@RequestMapping("/user/delete/{id}")
	@RequiresPermissions(PermissionResource.USER)
	@ClickStream(value="删除用户",  type=OperType.delete)
	public @ResponseBody boolean delete(@PathVariable("id") String id) {
		String userId = WebHelper.getUserIdFromSession();
		List<String> list = StringHelper.StringToList(id);
		if(list.contains(userId)){
			return false;
		}
		userService.delUser(list);
		userService.delUserRole(list);
		userService.delUserGroup(list);
		return true;
	}

	@RequestMapping("/user/status")
	@RequiresPermissions(PermissionResource.USER)
	@ClickStream(value="修改某用户状态",  type=OperType.edit)
	public @ResponseBody String editUserStatus(HttpServletRequest request) {
		String id = request.getParameter("id");
		String status = request.getParameter("status");
		userService.editUserStatus(id, status);
		return "true";
	}

	@RequestMapping("/user/validate")
	@RequiresPermissions(PermissionResource.USER)
	public @ResponseBody String validate(HttpServletRequest request) {
		int count = 0;
		String id = request.getParameter("id");
		String flag = request.getParameter("flag");
		if ("loginName".equals(flag)) {
			String loginName = request.getParameter("loginName");
			count = userService.getExistCount("login_name", loginName, id);
		} else if ("email".equals(flag)) {
			String email = request.getParameter("email");
			count = userService.getExistCount("email", email, id);
		} else if ("phone".equals(flag)) {
			String phone = request.getParameter("phone");
			count = userService.getExistCount("phone", phone, id);
		}
		if (count > 0) {
			return "{\"valid\": false}";
		}
		return "{\"valid\": true}";
	}

	@RequestMapping("/user/list")
	@RequiresPermissions(PermissionResource.USER)
	@ClickStream(value="浏览用户列表",  type=OperType.view)
	public String userList(HttpServletRequest request) {
		return "user/user_list";
	}

	@RequestMapping("/user/search")
	@ResponseBody
	@RequiresPermissions(PermissionResource.USER)
	@ClickStream(value="用户搜索用户列表",  type=OperType.search)
	public DataTablesPage<User> search(HttpServletRequest request) {
		return userPage(request);
	}

	@RequestMapping("/user/page")
	@ResponseBody
	@RequiresPermissions(PermissionResource.USER)
	public DataTablesPage<User> userPage(HttpServletRequest request) {
		String selectRange = request.getParameter("selectRange");
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
				orderColumn = "id";
			} else if (orderColumn.equals("2")) {
				orderColumn = "login_name";
			}
			PageHelper.orderBy(orderColumn + " " + orderDir);
		}
		if (!StringHelper.isNullOrBlank(searchValue)) {
			if ("loginName".equals(selectRange)) {
				selectRange = "login_name";
			}
		}
		List<User> list = userService.getUsers(selectRange, searchValue);
		DataTablesPage<User> page = new DataTablesPage<User>(list, draw, start, pageSize);
		return page;
	}

}