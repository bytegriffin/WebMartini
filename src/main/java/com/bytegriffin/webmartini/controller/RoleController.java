package com.bytegriffin.webmartini.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.bytegriffin.webmartini.domain.Group;
import com.bytegriffin.webmartini.domain.Menu;
import com.bytegriffin.webmartini.domain.Role;
import com.bytegriffin.webmartini.domain.RoleRef;
import com.bytegriffin.webmartini.domain.User;
import com.bytegriffin.webmartini.interceptor.PermissionResource;
import com.bytegriffin.webmartini.log.ClickStream;
import com.bytegriffin.webmartini.log.OperType;
import com.bytegriffin.webmartini.service.RoleService;
import com.bytegriffin.webmartini.util.DataTablesPage;
import com.bytegriffin.webmartini.util.ExcelUtil;
import com.bytegriffin.webmartini.util.PdfUtil;
import com.bytegriffin.webmartini.util.StringHelper;
import com.github.pagehelper.PageHelper;


@Controller
public class RoleController {

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "/role/import/excel", method = RequestMethod.GET)
	@RequiresPermissions(PermissionResource.ROLE)
	@ClickStream(value="用户正在浏览批量导入角色数据页面",  type=OperType.view)
	public String importxls(HttpServletRequest request) {
		return "role/role_import";
	}

	@RequestMapping(value = "/role/import/excel", method = RequestMethod.POST)
	@RequiresPermissions(PermissionResource.ROLE)
	@ResponseBody
	@ClickStream(value="用户正在批量导入角色数据",  type=OperType.import_excel)
	public String importxls(@RequestParam(value = "file") MultipartFile file,HttpServletRequest request) {
		try{
			if(file.isEmpty()){
				return "{\"status\": false,   \"message\": \"导入的excel文件为空，请检查后重新导入\"}";
			}
			
			String parentId = "0";//默认是顶级角色
			String code = roleService.getCode(parentId);
			List<Role> rolelist = new ArrayList<Role>();
			List<Map<Integer,String>> exceldata = ExcelUtil.readExcel(file.getInputStream());
			if(exceldata.size() == 0){
				return "{\"status\": false,   \"message\": \"导入的excel文件内容为空，请按照模板填写内容后重新导入\"}";
			}
			for(Map<Integer,String> map : exceldata){
				Role role = new Role();
				role.setName(map.get(0));
				if(!StringHelper.isNullOrBlank(map.get(1))){
						role.setDescription(map.get(1));
				}
				role.setParentId(parentId);
				role.setCode(code);
				code = StringHelper.getNextCode(role.getCode());
				rolelist.add(role);
			}
			roleService.insertRoles(rolelist);
		} catch (Exception e) {
			return "{\"status\": false,   \"message\": \""+e.getMessage().replaceAll("\"", "")+"\"}";
		}
		return "{\"status\": true,   \"message\": \"导入成功\"}";
	}

	@RequestMapping("/role/export/pdf")
	@RequiresPermissions(PermissionResource.ROLE)
	@ClickStream(value="用户导出pdf格式的角色数据",  type=OperType.exp_pdf)
	public void exportpdf(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException {
		String searchValue = request.getParameter("searchValue");
		List<Role> list = roleService.getRoles(searchValue);

		String title = "角色列表";
		String[] head = {"角色名称", "角色描述" };
		List<String[]> exceldata = new ArrayList<String[]>();
		for(Role role : list){
			String[] data = new String[head.length];
			data[0] = role.getName();
			data[1] = StringHelper.isNullOrBlank(role.getDescription()) ? "" : role.getDescription();
			exceldata.add(data);
		}

		PdfUtil.exportPDF(response,"角色列表.pdf", title, head, exceldata);
	}
	
	@RequestMapping("/role/export/excel")
	@RequiresPermissions(PermissionResource.ROLE)
	@ClickStream(value="用户导出excel格式的excel数据",  type=OperType.exp_excel)
	public void exportxls(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException {
		String searchValue = request.getParameter("searchValue");
		List<Role> list = roleService.getRoles(searchValue);

		String sheetName = "角色列表" ;
		String[] titles = {"角色名称", "角色描述"};
		List<String[]> exceldata = new ArrayList<String[]>();
		for(Role role : list){
			String[] data = new String[titles.length];
			data[0] = role.getName();
			data[1] = StringHelper.isNullOrBlank(role.getDescription()) ? "" : role.getDescription();
			exceldata.add(data);
		}

   ExcelUtil.writeToFile(response,"角色列表.xlsx", sheetName, titles, exceldata);
	}

	@RequestMapping("/role/suggest")
	@ResponseBody
	@RequiresPermissions(PermissionResource.ROLE)
	@ClickStream(value="用户输入关键字，准备搜索角色数据",  type=OperType.sugguest)
	public List<Role> suggest(HttpServletRequest request) {
		String searchValue = request.getParameter("searchValue");
		List<Role>	roleList = roleService.suggest("name", searchValue);
		return roleList;
	}

	@RequestMapping("/role/delete/{id}")
	@RequiresPermissions(PermissionResource.ROLE)
	@ClickStream(value="用户删除某角色",  type=OperType.delete)
	public @ResponseBody String delete(@PathVariable("id") String id) {
		Role role = roleService.getRoleById(id);
		if(role != null){
			int count = roleService.getChildCount(role.getCode());
			if(count > 1){
				return "false";
			}
		}
		roleService.delRole(id);
		roleService.delMenuRole(id);
		roleService.delUserRole(id);
		roleService.delGroupRole(id);
		return "true";
	}

	@RequestMapping("/role/validate")
	@RequiresPermissions(PermissionResource.ROLE)
	public @ResponseBody String validate(HttpServletRequest request) {
		int count = 0;
		String id = request.getParameter("id");
		String flag = request.getParameter("flag");
		if ("name".equals(flag)) {
			String name = request.getParameter("name");
			count = roleService.getExistCount(id, name);
		}
		if (count > 0) {
			return "{\"valid\": false}";
		}
		return "{\"valid\": true}";
	}

	@RequestMapping(value="/role/add", method=RequestMethod.GET)
	@RequiresPermissions(PermissionResource.ROLE)
	@ClickStream(value="用户打开新增角色页面",  type=OperType.view)
	public String openAddRole(HttpServletRequest request) {
		List<Role> list = roleService.getRoles(null);
		request.setAttribute("roles", list);
		return "role/role_add";
	}

	@RequestMapping(value="/role/add", method=RequestMethod.POST)
	@RequiresPermissions(PermissionResource.ROLE)
	@ClickStream(value="用户新增了一个角色",  type=OperType.add)
	public @ResponseBody String addRole(HttpServletRequest request) {
		String name = request.getParameter("name");
		String parentId = request.getParameter("parentId");
		String description = request.getParameter("description");
		String code = roleService.getCode(parentId);
		Role role = new Role();
		role.setName(name);
		role.setCode(code);
		role.setParentId(parentId);
		if(!StringHelper.isNullOrBlank(description)){
			role.setDescription(description);
		}
		roleService.insertRole(role);
		return "true";
	}

	@RequestMapping(value="/role/edit/{id}",method=RequestMethod.GET)
	@RequiresPermissions(PermissionResource.ROLE)
	@ClickStream(value="用户打开修改角色页面",  type=OperType.view)
	public String openEditMenu(@PathVariable("id") String id,Model model) {
		Role role = roleService.getRoleById(id);
		List<Role> list = roleService.getEditRoles(role.getCode());
		model.addAttribute("role",role);
		model.addAttribute("roleList",list);
		return "role/role_edit";
	}

	@RequestMapping(value="/role/edit", method=RequestMethod.POST)
	@RequiresPermissions(PermissionResource.ROLE)
	@ClickStream(value="用户修改了一个角色",  type=OperType.edit)
	public @ResponseBody String editRole(HttpServletRequest request) {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String parentId = request.getParameter("parentId");
		String description = request.getParameter("description");
		Role role = roleService.getRoleById(id);
		String orginCode = role.getCode();
		role.setId(id);
		role.setName(name);
		if(role.getParentId()!=null && parentId != null && !role.getParentId().equals(parentId)){
			role.setParentId(parentId);
			String code = roleService.getCode(parentId);
			role.setCode(code);
		}
		role.setDescription(description);
		roleService.editRole(role);
		//修改子角色的code
		List<Role> childRole = roleService.getChildRoles(orginCode);
		for(Role m : childRole){
			Role childs = roleService.getRoleById(m.getParentId());
			String childcode = StringHelper.getChildCode(m.getCode(), childs.getCode());
			m.setCode(childcode);
			roleService.editRole(m);
		}
		return "true";
	}

	@RequestMapping("/role/list")
	@RequiresPermissions(PermissionResource.ROLE)
	@ClickStream(value="用户浏览角色列表",  type=OperType.view)
	public String roleList(HttpServletRequest request) {
		return "role/role_list";
	}

	@RequestMapping("/role/menu/{id}")
	@RequiresPermissions(PermissionResource.ROLE)
	@ClickStream(value="用户浏览某角色关联的菜单列表",  type=OperType.view)
	public String roleMenu(@PathVariable("id") String id,HttpServletRequest request) {
		request.setAttribute("role_id", id);
		return "role/role_menu";
	}

	@RequestMapping("/role/menu_tree/{id}")
	@ResponseBody
	@RequiresPermissions(PermissionResource.ROLE)
	public List<Menu> openRoleMenuTree(@PathVariable("id") String id,HttpServletRequest request) {
		List<Menu> list = roleService.getMenuTrees();
		List<String> rrlist = roleService.getMenuIdsByRoleId(id);
		for(Menu m : list){
			if(rrlist.contains( m.getId()+"" )){
				m.setChecked(true);
			}
		}
		return list;
	}

	/**
	   *    用户授权菜单后，用户必须重新登陆才能看见菜单
	 * @param request
	 * @return
	 */
 @RequestMapping("/role/menu_ref")
 @ResponseBody
 @RequiresPermissions(PermissionResource.ROLE)
 @ClickStream(value="用户设置某角色关联的菜单列表",  type=OperType.role_menu)
	public String roleMenuRef(HttpServletRequest request) {
		String menu_ids = request.getParameter("menu_ids");
		String role_id = request.getParameter("role_id");
		roleService.delMenuRole(role_id);
		if(!StringHelper.isNullOrBlank(menu_ids)){
			 List<RoleRef> strList = StringHelper.StringToRoleRefList(role_id, menu_ids);
			 roleService.insertMenuRole(strList);
		}		
		return "true";
	}

	@RequestMapping("/role/user/{id}")
	@RequiresPermissions(PermissionResource.ROLE)
	@ClickStream(value="用户浏览某角色关联的用户列表",  type=OperType.view)
	public String roleUser(@PathVariable("id") String id,HttpServletRequest request) {
		List<User> list = roleService.getEnableUsers();
		List<String> ulist = roleService.getUserIdsByRoleId(id);
		for(User u : list){
			if(ulist.contains( u.getId()+"" )){
				u.setChecked(true);
			}
		}
		request.setAttribute("list", list);
		request.setAttribute("role_id", id);
		return "role/role_user";
	}

	@RequestMapping("/role/user_ref")
	@ResponseBody
	@RequiresPermissions(PermissionResource.ROLE)
	@ClickStream(value="用户设置某角色关联的用户列表",  type=OperType.role_user)
	public String roleUserRef(HttpServletRequest request) {
		 String[] userids = request.getParameterValues("user_ids[]");
			String role_id = request.getParameter("role_id");
			roleService.delUserRole(role_id);
			if(userids != null){
				 List<RoleRef> strList = StringHelper.StringToRoleRefList(role_id, userids);
				 roleService.insertUserRole(strList);
			}
			return "true";
	}

	@RequestMapping("/role/group/{id}")
	@RequiresPermissions(PermissionResource.ROLE)
	@ClickStream(value="用户浏览某角色关联组织列表",  type=OperType.view)
	public String roleGroup(@PathVariable("id") String id,HttpServletRequest request) {
		request.setAttribute("role_id", id);
		return "role/role_group";
	}

	@RequestMapping("/role/group_tree/{id}")
	@ResponseBody
	@RequiresPermissions(PermissionResource.ROLE)
	public List<Group> roleGroupTree(@PathVariable("id") String id,HttpServletRequest request) {
		List<Group> list = roleService.getGroupTrees();
		List<String> rrlist = roleService.getGroupIdsByRoleId(id);
		for(Group m : list){
			if(rrlist.contains( m.getId()+"" )){
				m.setChecked(true);
			}
		}
		return list;
	}

 @RequestMapping("/role/group_ref")
 @ResponseBody
 @RequiresPermissions(PermissionResource.ROLE)
 @ClickStream(value="用户设置某角色关联组织列表",  type=OperType.role_group)
	public String roleGroupRef(HttpServletRequest request) {
			String group_ids = request.getParameter("group_ids");
			String role_id = request.getParameter("role_id");
			roleService.delGroupRole(role_id);
			if(!StringHelper.isNullOrBlank(group_ids)){
				 List<RoleRef> strList = StringHelper.StringToRoleRefList(role_id, group_ids);
				 roleService.insertGroupRole(strList);
			}
			return "true";
	}

	@RequestMapping("/role/tree")
	@ResponseBody
	@RequiresPermissions(PermissionResource.ROLE)
	public List<Role> getRoleTree(HttpServletRequest request) {
		List<Role> list = roleService.getRoles(null);
		if(list.size() == 0){
			Role role = new Role();
			role.setId("0");
			role.setName("无数据");
			list.add(role);
		}
		request.setAttribute("roles", list);
		return list;
	}

	@RequestMapping("/role/search")
	@ResponseBody
	@RequiresPermissions(PermissionResource.ROLE)
	@ClickStream(value="用户搜索角色列表",  type=OperType.search)
	public DataTablesPage<Role> search(HttpServletRequest request) {
		return rolePage(request);
	}

	@RequestMapping("/role/page")
	@ResponseBody
	@RequiresPermissions(PermissionResource.ROLE)
	public DataTablesPage<Role> rolePage(HttpServletRequest request) {
		String code = request.getParameter("code");
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
			}else if (orderColumn.equals("2")) {
				orderColumn = "name";
			}
			PageHelper.orderBy(orderColumn + " " + orderDir);
		}

		List<Role> list = null;
		if(StringHelper.isNullOrBlank(code)){
			list = roleService.getRoles(searchValue);
		} else {
			list = roleService.getCascadeRoles(code);
		}
		int i = 0;
		for(Role role : list){
			i += 1;
			role.setNum(i);
		}
		DataTablesPage<Role> page = new DataTablesPage<Role>(list,draw,start,pageSize);
		return page;
	}

}
