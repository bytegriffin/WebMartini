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
import com.mullet.backend.entity.Menu;
import com.mullet.backend.entity.User;
import com.mullet.backend.interceptor.PermissionResource;
import com.mullet.backend.log.ClickStream;
import com.mullet.backend.log.OperType;
import com.mullet.backend.service.MenuService;
import com.mullet.backend.util.ArithUtil;
import com.mullet.backend.util.DataTablesPage;
import com.mullet.backend.util.ExcelUtil;
import com.mullet.backend.util.PdfUtil;
import com.mullet.backend.util.StringHelper;
import com.mullet.backend.util.WebHelper;

@Controller
public class MenuController {

	@Autowired
	private MenuService menuService;

	@RequestMapping(value = "/menu/import/excel", method = RequestMethod.GET)
	@RequiresPermissions(PermissionResource.MENU)
	@ClickStream(value="用户打开批量导入菜单数据的页面",  type=OperType.view)
	public String importxls(HttpServletRequest request) {
		return "menu/menu_import";
	}

	@RequestMapping(value = "/menu/import/excel", method = RequestMethod.POST)
	@RequiresPermissions(PermissionResource.MENU)
	@ResponseBody
	@ClickStream(value="用户批量导入excel格式的菜单数据",  type=OperType.import_excel)
	public String importxls(@RequestParam(value = "file") MultipartFile file,HttpServletRequest request) {
		try{
			if(file.isEmpty()){
				return "{\"status\": false,   \"message\": \"导入的excel文件为空，请检查后重新导入\"}";
			}
			String parentId = "0";//默认是顶级菜单
			String code = menuService.getCode(parentId);
			List<Menu> menulist = new ArrayList<Menu>();
			List<Map<Integer,String>> exceldata = ExcelUtil.readExcel(file.getInputStream());
			if(exceldata.size() == 0){
				return "{\"status\": false,   \"message\": \"导入的excel文件内容为空，请按照模板填写内容后重新导入\"}";
			}
			for(Map<Integer,String> map : exceldata){
				Menu menu = new Menu();
				menu.setName(map.get(0));
				menu.setUrl(map.get(1));
				menu.setLevels(ArithUtil.getIntValue(map.get(2)));
				if(!StringHelper.isNullOrBlank(map.get(3))){
					 menu.setPriority(ArithUtil.getIntValue(map.get(3)));
				}
				if(!StringHelper.isNullOrBlank(map.get(4))){
					 menu.setDescription(map.get(4));
				}
				menu.setParentId(parentId);
				menu.setCode(code);
				code = StringHelper.getNextCode(menu.getCode());
				menu.setStatus(Menu.ENABLE_STATUS);
				menulist.add(menu);
			}
			menuService.insertMenus(menulist);
		} catch (Exception e) {e.printStackTrace();
			 return "{\"status\": false,   \"message\": \""+e.getMessage().replaceAll("\"", "")+"\"}";
		}
		return "{\"status\": true,   \"message\": \"导入成功\"}";
	}
	
	@RequestMapping("/menu/export/pdf")
	@RequiresPermissions(PermissionResource.MENU)
	@ClickStream(value="用户导出pdf格式的菜单数据",  type=OperType.exp_pdf)
	public void exportpdf(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException {
		String searchValue = request.getParameter("searchValue");
		List<Menu> list = menuService.getMenus(searchValue);

		String title = "菜单列表";
		String[] head = {"菜单名称", "菜单链接", "菜单层级", "菜单权重",  "菜单描述"};
		List<String[]> exceldata = new ArrayList<String[]>();
		for(Menu menu : list){
			String[] data = new String[head.length];
			data[0] = menu.getName();
			data[1] = StringHelper.isNullOrBlank(menu.getUrl()) ? "" : menu.getUrl();
			data[2] = Menu.getLevels(menu.getLevels());
			data[3] = menu.getPriority()+"";
			data[4] = StringHelper.isNullOrBlank(menu.getDescription()) ? "" : menu.getDescription();
			exceldata.add(data);
		}

		PdfUtil.exportPDF(response,"菜单列表.pdf", title, head, exceldata);
	}
	
	@RequestMapping("/menu/export/excel")
	@RequiresPermissions(PermissionResource.MENU)
	@ClickStream(value="用户导出excel格式的菜单数据",  type=OperType.exp_excel)
	public void exportxls(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException {
		String searchValue = request.getParameter("searchValue");
		List<Menu> list = menuService.getMenus(searchValue);

		String sheetName = "菜单列表" ;
		String[] titles = {"菜单名称", "菜单链接", "菜单层级", "菜单权重",  "菜单描述"};
		List<String[]> exceldata = new ArrayList<String[]>();
		for(Menu menu : list){
			String[] data = new String[titles.length];
			data[0] = menu.getName();
			data[1] = StringHelper.isNullOrBlank(menu.getUrl()) ? "" : menu.getUrl();
			data[2] = Menu.getLevels(menu.getLevels());
			data[3] = menu.getPriority()+"";
			data[4] = StringHelper.isNullOrBlank(menu.getDescription()) ? "" : menu.getDescription();
			exceldata.add(data);
		}

   ExcelUtil.writeToFile(response,"菜单列表.xlsx", sheetName, titles, exceldata);
	}

	@RequestMapping("/menu/suggest")
	@ResponseBody
	@RequiresPermissions(PermissionResource.MENU)
	@ClickStream(value="用户输入关键字，准备搜索菜单数据",  type=OperType.sugguest)
	public List<Menu> suggest(HttpServletRequest request) {
		String searchValue = request.getParameter("searchValue");
		List<Menu>	menuList = menuService.suggest("name", searchValue);
		return menuList;
	}

	/**
	 *    因为菜单有父子关系，处理太麻烦，所以没有提供批量删除功能
	 * @param id
	 * @return
	 */
	@RequestMapping("/menu/delete/{id}")
	@RequiresPermissions(PermissionResource.MENU)
	@ClickStream(value="用户输入删除菜单数据",  type=OperType.delete)
	public @ResponseBody String delete(@PathVariable("id") String id) {
		Menu menu = menuService.getMenuById(id);
		if(menu != null){
			int count = menuService.getChildCount(menu.getCode());
			if(count > 1){
				return "false";
			}
		}
		menuService.delMenu(id);
		menuService.delMenuRole(id);
		return "true";
	}

	@RequestMapping(value="/menu/edit/{id}",method=RequestMethod.GET)
	@RequiresPermissions(PermissionResource.MENU)
	@ClickStream(value="用户打开某一菜单编辑页面",  type=OperType.view)
	public String openEditMenu(@PathVariable("id") String id,Model model) {
		Menu menu = menuService.getMenuById(id);
		List<Menu> list = menuService.getEditMenus(menu.getCode());
		model.addAttribute("menu",menu);
		model.addAttribute("menuList",list);
		return "menu/menu_edit";
	}

	@RequestMapping(value="/menu/edit", method=RequestMethod.POST)
	@RequiresPermissions(PermissionResource.MENU)
	@ClickStream(value="用户修改了某一菜单数据",  type=OperType.edit)
	public @ResponseBody String editMenu(HttpServletRequest request) {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String url = request.getParameter("url");
		String icon = request.getParameter("icon");
		String status = request.getParameter("status");
		String priority = request.getParameter("priority");
		String parentId = request.getParameter("parentId");
		String description = request.getParameter("description");
		String levels = request.getParameter("levels");
		Menu menu = menuService.getMenuById(id);
		String orginCode = menu.getCode();
		menu.setName(name);
		menu.setUrl(url);
		menu.setIcon(Menu.getIcon(icon));
		menu.setStatus(Menu.getStatuts(status));
		menu.setLevels(Integer.valueOf(levels));
		if(menu.getParentId() != null && parentId !=null && !menu.getParentId().equals(parentId)){
			menu.setParentId(parentId);
			String code = menuService.getCode(parentId);
			menu.setCode(code);
		}
		if(StringHelper.isNullOrBlank(priority)){
			menu.setPriority(null);
		}else{
			menu.setPriority(Integer.valueOf(priority));
		}
		menu.setDescription(description);
		menuService.editMenu(menu);
		//修改子菜单的code
		List<Menu> childMenu = menuService.getChildMenus(orginCode);
		for(Menu m : childMenu){
			Menu childs = menuService.getMenuById(m.getParentId());
			String childcode = StringHelper.getChildCode(m.getCode(), childs.getCode());
			m.setCode(childcode);
			menuService.editMenu(m);
		}
		return "true";
	}

	@RequestMapping("/menu/validate")
	@RequiresPermissions(PermissionResource.MENU)
	public @ResponseBody String validate(HttpServletRequest request) {
		int count = 0;
		String id = request.getParameter("id");
		String flag = request.getParameter("flag");
		if ("name".equals(flag)) {
			String name = request.getParameter("name");
			count = menuService.getExistCount(id, "name", name);
		} else if ("url".equals(flag)) {
			String url = request.getParameter("url");
			count = menuService.getExistCount(id,"url", url);
		} else if ("priority".equals(flag)) {
			String priority = request.getParameter("priority");
			count = menuService.getExistCount(id, "priority", priority);
		}
		if (count > 0) {
			return "{\"valid\": false}";
		}
		return "{\"valid\": true}";
	}

	@RequestMapping(value="/menu/add", method=RequestMethod.GET)
	@RequiresPermissions(PermissionResource.MENU)
	@ClickStream(value="用户打开了新增菜单页面",  type=OperType.view)
	public String openAddMenu(HttpServletRequest request) {
		List<Menu> list = menuService.getMenus(null);
		request.setAttribute("menus", list);
		return "menu/menu_add";
	}

	@RequestMapping(value="/menu/add", method=RequestMethod.POST)
	@RequiresPermissions(PermissionResource.MENU)
	@ClickStream(value="用户新增了一个菜单数据",  type=OperType.add)
	public @ResponseBody String addMenu(HttpServletRequest request) {
		String name = request.getParameter("name");
		String url = request.getParameter("url");
		String icon = request.getParameter("icon");
		String status = request.getParameter("status");
		String priority = request.getParameter("priority");
		String parentId = request.getParameter("parentId");
		String description = request.getParameter("description");
		String levels = request.getParameter("levels");
		String code = menuService.getCode(parentId);
		Menu menu = new Menu();
		menu.setName(name);
		menu.setUrl(url);
		menu.setCode(code);
		menu.setIcon(Menu.getIcon(icon));
		menu.setStatus(Menu.getStatuts(status));
		menu.setParentId(parentId);
		menu.setLevels(Integer.valueOf(levels));
		if(!StringHelper.isNullOrBlank(priority)){
			menu.setPriority(Integer.valueOf(priority));
		}
		if(!StringHelper.isNullOrBlank(description)){
			menu.setDescription(description);
		}
		menuService.insertMenu(menu);
		return "true";
	}

	@RequestMapping("/menu/list")
	@RequiresPermissions(PermissionResource.MENU)
	@ClickStream(value="用户浏览菜单列表",  type=OperType.view)
	public String menuList() {
		return "menu/menu_list";
	}

	@RequestMapping("/menu/status")
	@RequiresPermissions(PermissionResource.MENU)
	@ClickStream(value="用户修改某一菜单状态",  type=OperType.edit)
	public @ResponseBody String editMenuStatus(HttpServletRequest request) {
		String id = request.getParameter("id");
		String status = request.getParameter("status");
		menuService.editMenuStatus(id, status);
		return "true";
	}

	@RequestMapping("/menu/search")
	@ResponseBody
	@RequiresPermissions(PermissionResource.MENU)
	@ClickStream(value="用户搜索菜单列表",  type=OperType.search)
	public DataTablesPage<Menu> search(HttpServletRequest request) {
		return menuPage(request);
	}

	@RequestMapping("/menu/page")
	@ResponseBody
	@RequiresPermissions(PermissionResource.MENU)
	public DataTablesPage<Menu> menuPage(HttpServletRequest request) {
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
				orderColumn = "code";
			}else if (orderColumn.equals("2")) {
				orderColumn = "code";
			}
			PageHelper.orderBy(orderColumn + " " + orderDir);
		}
		
		List<Menu> list = menuService.getMenus(searchValue);
		int i = 0;
		for(Menu menu : list){
			i += 1;
			menu.setNum(i);
		}
		DataTablesPage<Menu> page = new DataTablesPage<Menu>(list,draw,start,pageSize);
		return page;
	}

   /**
    *  导航栏
 * @param req
 * @return
    */
	@RequestMapping("/layout/menus")
	public String loadSidebar(HttpServletRequest req) {
		User user = WebHelper.getUserFromSession();
		List<Menu> list = menuService.getEnabledMenus(user.getId());
		String parentid = "0";
		List<Menu> newList = travelTree(list, parentid);
		req.setAttribute("menus", newList);
		return "layout/menu";
	}

   /**
	 *   迭代菜单
	 * 
  * @param list
  * @param parentId
  * @return
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

}
