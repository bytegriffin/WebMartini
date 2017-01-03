package com.mullet.backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mullet.backend.dao.RoleDao;
import com.mullet.backend.entity.Group;
import com.mullet.backend.entity.Menu;
import com.mullet.backend.entity.Role;
import com.mullet.backend.entity.RoleRef;
import com.mullet.backend.entity.User;
import com.mullet.backend.service.RoleService;
import com.mullet.backend.util.StringHelper;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleDao roleDao;

	@Override
	public synchronized String getCode(String parentId) {
		Role m = this.roleDao.getMaxCode(parentId);
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
	public void insertMenuRole(List<RoleRef> list) {
		 this.roleDao.insertMenuRole(list);
	}

	@Override
	public void insertUserRole(List<RoleRef> list){
		 this.roleDao.insertUserRole(list);
	}

	@Override
	public void insertGroupRole(List<RoleRef> list){
		 this.roleDao.insertGroupRole(list);
	}

	@Override
	public List<Role> suggest(String name, String value) {
		 List<Role> list = this.roleDao.suggest(name, value);
		 return list;
	}

	@Override
	public List<Role> getRoles(String searchValue) {
		 return this.roleDao.getRoles(searchValue);
	}

	@Override
	public int getExistCount(String id, String value) {
		 int count = this.roleDao.getExistCount(id, value);
		 return count;
	}

	@Override
	public Role getRoleById(String roleId) {
		 return this.roleDao.getRoleById(roleId);
	}

	@Override
	public List<String> getMenuIdsByRoleId(String roleId) {
		 return this.roleDao.getMenuIdsByRoleId(roleId);
	}
	
	@Override
	public List<String> getUserIdsByRoleId(String roleId) {
		 return this.roleDao.getUserIdsByRoleId(roleId);
	}

	@Override
	public void insertRole(Role role) {
		 this.roleDao.insertRole(role);
	}
	
	@Override
	public List<Role> getEditRoles(String code){
		return this.roleDao.getEditRoles(code);
	}
	
	@Override
	public List<Role> getChildRoles(String code){
		return this.roleDao.getChildRoles(code);
	}

	@Override
	public void editRole(Role role) {
		 this.roleDao.editRole(role);
	}
	
	@Override
	public int getChildCount(String code){
		return this.roleDao.getChildCount(code);
	}

	@Override
	public void delRole(String id) {
		 this.roleDao.delRole(id);
	}

	@Override
	public void delMenuRole(String id) {
		 this.roleDao.delMenuRole(id);
	}

	@Override
	public void delUserRole(String id) {
		 this.roleDao.delUserRole(id);
	}

	@Override
	public void delGroupRole(String id) {
			this.roleDao.delGroupRole(id);
	}

	@Override
	public List<Menu> getMenuTrees() {
		return this.roleDao.getMenuTrees();
	}

	@Override
	public List<User> getEnableUsers() {
		return this.roleDao.getEnableUsers();
	}

	@Override
	public List<Group> getGroupTrees() {
		return this.roleDao.getGroupTrees();
	}

	@Override
	public List<String> getGroupIdsByRoleId(String roleId) {
		return this.roleDao.getGroupIdsByRoleId(roleId);
	}

	@Override
	public void insertRoles(List<Role> roles) {
		// TODO Auto-generated method stub
	  this.roleDao.insertRoles(roles);
	}

	@Override
	public List<Role> getCascadeRoles(String code) {
		// TODO Auto-generated method stub
		return this.roleDao.getCascadeRoles(code);
	}

}
