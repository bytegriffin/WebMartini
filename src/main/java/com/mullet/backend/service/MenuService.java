package com.mullet.backend.service;

import java.util.List;

import com.mullet.backend.entity.Menu;


public interface MenuService {

	String getCode(String parentId);
	
	List<Menu> getEditMenus(String code);

	List<Menu> suggest(String name, String value);

	List<Menu> getMenus(String searchValue);
	
	List<Menu> getChildMenus(String code);

	List<Menu> getEnabledMenus(String userId);

	Menu getMenuById(String menuId);

	void insertMenu(Menu menu);
	
	void insertMenus(List<Menu> menus);

	int getExistCount(String id, String name, String value);

	void editMenu(Menu menu);
	
	int getChildCount(String code);

	void delMenu(String id);
	
	void delMenuRole(String id);

	void editMenuStatus(String id, String status);
}