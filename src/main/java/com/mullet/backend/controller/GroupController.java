package com.mullet.backend.controller;

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

import com.github.pagehelper.PageHelper;
import com.mullet.backend.entity.Group;
import com.mullet.backend.entity.GroupRef;
import com.mullet.backend.entity.Role;
import com.mullet.backend.entity.User;
import com.mullet.backend.interceptor.PermissionResource;
import com.mullet.backend.log.ClickStream;
import com.mullet.backend.log.OperType;
import com.mullet.backend.service.GroupService;
import com.mullet.backend.util.DataTablesPage;
import com.mullet.backend.util.ExcelUtil;
import com.mullet.backend.util.PdfUtil;
import com.mullet.backend.util.StringHelper;


@Controller
public class GroupController {

	@Autowired
	private GroupService groupService;

	@RequestMapping(value = "/group/import/excel", method = RequestMethod.GET)
	@RequiresPermissions(PermissionResource.GROUP)
	@ClickStream(value="用户正在浏览批量导入组织数据页面",  type=OperType.view)
	public String importxls(HttpServletRequest request) {
		return "group/group_import";
	}

	@RequestMapping(value = "/group/import/excel", method = RequestMethod.POST)
	@RequiresPermissions(PermissionResource.GROUP)
	@ResponseBody
	@ClickStream(value="用户正在批量导入组织数据",  type=OperType.import_excel)
	public String importxls(@RequestParam(value = "file") MultipartFile file,HttpServletRequest request) {
		try{
			if(file.isEmpty()){
				return "{\"status\": false,   \"message\": \"导入的excel文件为空，请检查后重新导入\"}";
			}
			String parentId = "0";//默认是顶级组织
			String code = groupService.getCode(parentId);
			List<Group> grouplist = new ArrayList<Group>();
			List<Map<Integer,String>> exceldata = ExcelUtil.readExcel(file.getInputStream());
			if(exceldata.size() == 0){
				return "{\"status\": false,   \"message\": \"导入的excel文件内容为空，请按照模板填写内容后重新导入\"}";
			}
			for(Map<Integer,String> map : exceldata){
				Group group = new Group();
				group.setName(map.get(0));
				if(!StringHelper.isNullOrBlank(map.get(1))){
					group.setDescription(map.get(1));
				}
				group.setParentId(parentId);
				group.setCode(code);
				code = StringHelper.getNextCode(group.getCode());
				grouplist.add(group);
			}
			groupService.insertGroups(grouplist);
		} catch (Exception e) {
			return "{\"status\": false,  \"message\": \""+e.getMessage().replaceAll("\"", "")+"\"}";
		}
		return "{\"status\": true,   \"message\": \"导入成功\"}";
	}
	
	@RequestMapping("/group/export/pdf")
	@RequiresPermissions(PermissionResource.GROUP)
	@ClickStream(value="用户导出pdf格式的组织数据",  type=OperType.exp_pdf)
	public void exportpdf(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException {
		String searchValue = request.getParameter("searchValue");
		List<Group> list = groupService.getGroups(searchValue);

		String title = "组织列表";
		String[] head = {"角色名称", "组织描述" };
		List<String[]> exceldata = new ArrayList<String[]>();
		for(Group group : list){
			String[] data = new String[head.length];
			data[0] = group.getName();
			data[1] = StringHelper.isNullOrBlank(group.getDescription()) ? "" : group.getDescription();
			exceldata.add(data);
		}

		PdfUtil.exportPDF(response,"组织列表.pdf", title, head, exceldata);
	}
	
	@RequestMapping("/group/export/excel")
	@RequiresPermissions(PermissionResource.GROUP)
	@ClickStream(value="用户导出excel格式的组织数据",  type=OperType.exp_excel)
	public void exportxls(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException {
		String searchValue = request.getParameter("searchValue");
		List<Group> list = groupService.getGroups(searchValue);

		String sheetName = "组织列表" ;
		String[] titles = {"组织名称", "组织描述"};
		List<String[]> exceldata = new ArrayList<String[]>();
		for(Group group : list){
			String[] data = new String[titles.length];
			data[0] = group.getName();
			data[1] = StringHelper.isNullOrBlank(group.getDescription()) ? "" : group.getDescription();
			exceldata.add(data);
		}

   ExcelUtil.writeToFile(response,"组织列表.xlsx", sheetName, titles, exceldata);
	}

	@RequestMapping("/group/suggest")
	@ResponseBody
	@RequiresPermissions(PermissionResource.GROUP)
	@ClickStream(value="用户输入关键字，准备搜索组织数据",  type=OperType.sugguest)
	public List<Group> suggest(HttpServletRequest request) {
		String searchValue = request.getParameter("searchValue");
		List<Group> roleList = groupService.suggest("name", searchValue);
		return roleList;
	}

	@RequestMapping("/group/tree")
	@ResponseBody
	@RequiresPermissions(PermissionResource.GROUP)
	public List<Group> getRoleTree(HttpServletRequest request) {
		List<Group> list = groupService.getGroups(null);
		if (list.size() == 0) {
			Group group = new Group();
			group.setId("0");
			group.setName("无数据");
			list.add(group);
		}
		request.setAttribute("groups", list);
		return list;
	}
	
	@RequestMapping("/group/search")
	@ResponseBody
	@RequiresPermissions(PermissionResource.GROUP)
	@ClickStream(value="用户搜索日志列表",  type=OperType.search)
	public DataTablesPage<Group> groupSearch(HttpServletRequest request) {
		return groupPage(request);
	}

	@RequestMapping("/group/list")
	@RequiresPermissions("group/list")
	@ClickStream(value="用户浏览组织列表",  type=OperType.view)
	public String groupList() {
		return "group/group_list";
	}

	@RequestMapping("/group/page")
	@ResponseBody
	@RequiresPermissions(PermissionResource.GROUP)
	public DataTablesPage<Group> groupPage(HttpServletRequest request) {
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
				orderColumn = "code";
			}
			PageHelper.orderBy(orderColumn + " " + orderDir);
		}
		
		List<Group> list = null;
		if(StringHelper.isNullOrBlank(code)){
			list = groupService.getGroups(searchValue);
		} else {
			list = groupService.getCascadeGroups(code);
		}

		int i = 0;
		for(Group group : list){
			i += 1;
			group.setNum(i);
		}
		DataTablesPage<Group> page = new DataTablesPage<Group>(list,draw,start,pageSize);
		return page;
	}

	@RequestMapping("/group/validate")
	@RequiresPermissions(PermissionResource.GROUP)
	public @ResponseBody String validate(HttpServletRequest request) {
		int count = 0;
		String id = request.getParameter("id");
		String flag = request.getParameter("flag");
		if ("name".equals(flag)) {
			String name = request.getParameter("name");
			count = groupService.getExistCount(id, name);
		}
		if (count > 0) {
			return "{\"valid\": false}";
		}
		return "{\"valid\": true}";
	}

	@RequestMapping(value = "/group/add", method = RequestMethod.GET)
	@RequiresPermissions(PermissionResource.GROUP)
	@ClickStream(value="用户打开新增组织页面",  type=OperType.view)
	public String openAddGroup(HttpServletRequest request) {
		List<Group> list = groupService.getGroups(null);
		request.setAttribute("groups", list);
		return "group/group_add";
	}

	@RequestMapping(value = "/group/add", method = RequestMethod.POST)
	@RequiresPermissions(PermissionResource.GROUP)
	@ClickStream(value="用户新增了一个组织",  type=OperType.add)
	public @ResponseBody String addGroup(HttpServletRequest request) {
		String name = request.getParameter("name");
		String parentId = request.getParameter("parentId");
		String description = request.getParameter("description");
		String code = groupService.getCode(parentId);
		Group group = new Group();
		group.setName(name);
		group.setCode(code);
		group.setParentId(parentId);
		if (!StringHelper.isNullOrBlank(description)) {
			group.setDescription(description);
		}
		groupService.insertGroup(group);
		return "true";
	}

	@RequestMapping(value = "/group/edit/{id}", method = RequestMethod.GET)
	@RequiresPermissions(PermissionResource.GROUP)
	@ClickStream(value="用户打开修改组织页面",  type=OperType.view)
	public String openEditGroup(@PathVariable("id") String id, Model model) {
		Group group = groupService.getGroupById(id);
		List<Group> list = groupService.getEditGroups(group.getCode());
		model.addAttribute("group", group);
		model.addAttribute("groupList", list);
		return "group/group_edit";
	}

	@RequestMapping(value = "/group/edit", method = RequestMethod.POST)
	@RequiresPermissions(PermissionResource.GROUP)
	@ClickStream(value="用户修改了一个组织",  type=OperType.edit)
	public @ResponseBody String editGroup(HttpServletRequest request) {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String parentId = request.getParameter("parentId");
		String description = request.getParameter("description");
		Group group = groupService.getGroupById(id);
		String orginCode = group.getCode();
		group.setId(id);
		group.setName(name);
		if(group.getParentId()!=null && parentId != null && !group.getParentId().equals(parentId)){
			group.setParentId(parentId);
			String code = groupService.getCode(parentId);
			group.setCode(code);
		}
		group.setDescription(description);
		groupService.editGroup(group);

		//修改子组织的code
		List<Group> childGroup = groupService.getChildGroups(orginCode);
		for(Group m : childGroup){
			Group childs = groupService.getGroupById(m.getParentId());
			String childcode = StringHelper.getChildCode(m.getCode(), childs.getCode());
			m.setCode(childcode);
			groupService.editGroup(m);
		}
				
		return "true";
	}

	@RequestMapping("/group/delete/{id}")
	@RequiresPermissions(PermissionResource.GROUP)
	@ClickStream(value="用户修改了一个组织",  type=OperType.edit)
	public @ResponseBody String delete(@PathVariable("id") String id) {
		Group group = groupService.getGroupById(id);
		if(group != null){
			int count = groupService.getChildCount(group.getCode());
			if(count > 1){
				return "false";
			}
		}
		groupService.delGroup(id);
		groupService.delGroupUser(id);
		groupService.delGroupRole(id);
		return "true";
	}

	@RequestMapping("/group/user/{id}")
	@RequiresPermissions(PermissionResource.GROUP)
	@ClickStream(value="用户打开某组织下的关联用户列表页面",  type=OperType.view)
	public String openGroupUser(@PathVariable("id") String id, HttpServletRequest request) {
		List<User> list = groupService.getEnableUsers();
		List<String> ulist = groupService.getUserIdsByGroupId(id);
		for (User u : list) {
			if (ulist.contains(u.getId() + "")) {
				u.setChecked(true);
			}
		}
		request.setAttribute("list", list);
		request.setAttribute("group_id", id);
		return "group/group_user";
	}

	@RequestMapping("/group/user_ref")
	@ResponseBody
	@RequiresPermissions(PermissionResource.GROUP)
	@ClickStream(value="用户设置某组织下的关联用户列表",  type=OperType.group_user)
	public String groupUserRef(HttpServletRequest request) {
		String[] userids = request.getParameterValues("user_ids[]");
		String group_id = request.getParameter("group_id");
		groupService.delGroupUser(group_id);
		if (userids != null) {
			List<GroupRef> strList = StringHelper.StringToGroupRefList(group_id, userids);
			groupService.insertGroupUser(strList);
		}
		return "true";
	}

	@RequestMapping("/group/role/{id}")
	@RequiresPermissions(PermissionResource.GROUP)
	@ClickStream(value="用户打开某组织下的关联角色列表页面",  type=OperType.view)
	public String openRoleGroup(@PathVariable("id") String id, HttpServletRequest request) {
		request.setAttribute("group_id", id);
		return "group/group_role";
	}

	@RequestMapping("/group/role_tree/{id}")
	@ResponseBody
	@RequiresPermissions(PermissionResource.GROUP)
	public List<Role> groupRoleTree(@PathVariable("id") String id, HttpServletRequest request) {
		List<Role> list = groupService.getRoleTrees();
		List<String> rrlist = groupService.getRoleIdsByGroupId(id);
		for (Role m : list) {
			if (rrlist.contains(m.getId() + "")) {
				m.setChecked(true);
			}
		}
		return list;
	}

	@RequestMapping("/group/role_ref")
	@ResponseBody
	@RequiresPermissions(PermissionResource.GROUP)
	@ClickStream(value="用户设置某组织下的关联角色列表",  type=OperType.group_role)
	public String roleGroupRef(HttpServletRequest request) {
		String role_ids = request.getParameter("role_ids");
		String group_id = request.getParameter("group_id");
		groupService.delGroupRole(group_id);
		if (!StringHelper.isNullOrBlank(role_ids)) {
			List<GroupRef> strList = StringHelper.StringToGroupRefList(group_id, role_ids);
			groupService.insertGroupRole(strList);
		}
		return "true";
	}

}
