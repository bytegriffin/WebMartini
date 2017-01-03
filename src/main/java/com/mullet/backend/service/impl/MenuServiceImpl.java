package com.mullet.backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mullet.backend.dao.MenuDao;
import com.mullet.backend.entity.Menu;
import com.mullet.backend.service.MenuService;
import com.mullet.backend.util.StringHelper;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;

	@Override
	public	List<Menu> getChildMenus(String code){
		return this.menuDao.getChildMenus(code);
	}

	@Override
	public synchronized String getCode(String parentId) {
		Menu m = this.menuDao.getMaxCode(parentId);
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

	@Override
	public List<Menu> getEditMenus(String code){
		return this.menuDao.getEditMenus(code);
	}

	@Override
	public List<Menu> suggest(String name, String value) {
		List<Menu> list = this.menuDao.suggest(name, value);
		return list;
	}

	@Override
	public void editMenuStatus(String id, String status) {
		this.menuDao.editMenuStatus(id, status);
	}

	@Override
	public Menu getMenuById(String menuId) {
		return this.menuDao.getMenuById(menuId);
	}

	@Override
	public List<Menu> getMenus(String searchValue) {
		return this.menuDao.getMenus(searchValue);
	}

	public List<Menu> getEnabledMenus(String userId) {
		return this.menuDao.getEnabledMenus(userId);
	}

	@Override
	public int getExistCount(String id, String name, String value) {
		int count = this.menuDao.getExistCount(id, name, value);
		return count;
	}

	@Override
	public void insertMenu(Menu menu) {
		this.menuDao.insertMenu(menu);
	}

	@Override
	public void delMenu(String id) {
		this.menuDao.delMenu(id);
	}
	
	@Override
	public int getChildCount(String code){
		return this.menuDao.getChildCount(code);
	}

	@Override
	public void delMenuRole(String id) {
		this.menuDao.delMenuRole(id);
	}

	@Override
	public void editMenu(Menu menu) {
		this.menuDao.editMenu(menu);
	}

	@Override
	public void insertMenus(List<Menu> menus) {
		this.menuDao.insertMenus(menus);
	}

}
