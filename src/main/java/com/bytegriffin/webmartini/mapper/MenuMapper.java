package com.bytegriffin.webmartini.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bytegriffin.webmartini.domain.Menu;

@Mapper
public interface MenuMapper {

	List<Menu> getChildMenus(@Param("code") String code);

	Menu getMaxCode(@Param("parentId") String parentId);

	List<Menu> getEditMenus(@Param("code") String code);

	List<Menu> suggest(String name, String value);

	List<Menu> getMenus(@Param("searchValue") String searchValue);

	List<Menu> getEnabledMenus(@Param("userId") String userId);

	Menu getMenuById(String menuId);

	void insertMenu(Menu menu);

	void insertMenus(List<Menu> menus);

	int getExistCount(String id, String name, String value);

	void editMenu(Menu menu);

	void editMenuStatus(String id, String status);

	int getChildCount(@Param("code") String code);

	void delMenu(@Param("id") String id);

	void delMenuRole(@Param("id") String id);

}
