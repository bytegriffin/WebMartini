package com.bytegriffin.webmartini.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bytegriffin.webmartini.domain.Menu;
import com.bytegriffin.webmartini.mapper.MenuMapper;
import com.bytegriffin.webmartini.util.StringHelper;

@Service
@Transactional(rollbackFor = Throwable.class)
public class MenuService {

	@Autowired
	private MenuMapper menuMapper;

	
	public	List<Menu> getChildMenus(String code){
		return this.menuMapper.getChildMenus(code);
	}

	
	public synchronized String getCode(String parentId) {
		Menu m = this.menuMapper.getMaxCode(parentId);
		if (StringHelper.isNullOrBlank(m.getParentCode())) {
			m.setParentCode(StringHelper.ROOT_CODE);
		}
		if (StringHelper.isNullOrBlank(m.getCode())) {
			if(StringHelper.ROOT_CODE.equals(m.getParentCode())){
				m.setCode(StringHelper.FIRST_CODE);
			}else{
				m.setCode(m.getParentCode() + StringHelper.SUB_CODE);
			}
		} else {
			m.setCode(StringHelper.getNextCode(m.getCode()));	
		}
		return m.getCode();
	}

	
	public List<Menu> getEditMenus(String code){
		return this.menuMapper.getEditMenus(code);
	}

	
	public List<Menu> suggest(String name, String value) {
		List<Menu> list = this.menuMapper.suggest(name, value);
		return list;
	}

	
	public void editMenuStatus(String id, String status) {
		this.menuMapper.editMenuStatus(id, status);
	}

	
	public Menu getMenuById(String menuId) {
		return this.menuMapper.getMenuById(menuId);
	}

	
	public List<Menu> getMenus(String searchValue) {
		return this.menuMapper.getMenus(searchValue);
	}

	public List<Menu> getEnabledMenus(String userId) {
		return this.menuMapper.getEnabledMenus(userId);
	}

	
	public int getExistCount(String id, String name, String value) {
		int count = this.menuMapper.getExistCount(id, name, value);
		return count;
	}

	
	public void insertMenu(Menu menu) {
		this.menuMapper.insertMenu(menu);
	}

	
	public void delMenu(String id) {
		this.menuMapper.delMenu(id);
	}
	
	
	public int getChildCount(String code){
		return this.menuMapper.getChildCount(code);
	}

	
	public void delMenuRole(String id) {
		this.menuMapper.delMenuRole(id);
	}

	
	public void editMenu(Menu menu) {
		this.menuMapper.editMenu(menu);
	}

	public void insertMenus(List<Menu> menus) {
		this.menuMapper.insertMenus(menus);
	}

}
