package com.bytegriffin.webmartini.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bytegriffin.webmartini.domain.Group;
import com.bytegriffin.webmartini.domain.Menu;
import com.bytegriffin.webmartini.domain.Role;
import com.bytegriffin.webmartini.domain.RoleRef;
import com.bytegriffin.webmartini.domain.User;
import com.bytegriffin.webmartini.mapper.RoleMapper;
import com.bytegriffin.webmartini.util.StringHelper;

@Service
@Transactional(rollbackFor = Throwable.class)
public class RoleService {

	@Autowired
	private RoleMapper roleMapper;

	public synchronized String getCode(String parentId) {
		Role m = this.roleMapper.getMaxCode(parentId);
		if (StringHelper.isNullOrBlank(m.getParentCode())) {
			m.setParentCode(StringHelper.ROOT_CODE);
		}
		if (StringHelper.isNullOrBlank(m.getCode())) {
			if (StringHelper.ROOT_CODE.equals(m.getParentCode())) {
				m.setCode(StringHelper.FIRST_CODE);
			} else {
				m.setCode(m.getParentCode() + StringHelper.SUB_CODE);
			}
		} else {
			m.setCode(StringHelper.getNextCode(m.getCode()));
		}
		return m.getCode();
	}

	public void insertMenuRole(List<RoleRef> list) {
		this.roleMapper.insertMenuRole(list);
	}

	public void insertUserRole(List<RoleRef> list) {
		this.roleMapper.insertUserRole(list);
	}

	public void insertGroupRole(List<RoleRef> list) {
		this.roleMapper.insertGroupRole(list);
	}

	public List<Role> suggest(String name, String value) {
		List<Role> list = this.roleMapper.suggest(name, value);
		return list;
	}

	public List<Role> getRoles(String searchValue) {
		return this.roleMapper.getRoles(searchValue);
	}

	public int getExistCount(String id, String value) {
		int count = this.roleMapper.getExistCount(id, value);
		return count;
	}

	public Role getRoleById(String roleId) {
		return this.roleMapper.getRoleById(roleId);
	}

	public List<String> getMenuIdsByRoleId(String roleId) {
		return this.roleMapper.getMenuIdsByRoleId(roleId);
	}

	public List<String> getUserIdsByRoleId(String roleId) {
		return this.roleMapper.getUserIdsByRoleId(roleId);
	}

	public void insertRole(Role role) {
		this.roleMapper.insertRole(role);
	}

	public List<Role> getEditRoles(String code) {
		return this.roleMapper.getEditRoles(code);
	}

	public List<Role> getChildRoles(String code) {
		return this.roleMapper.getChildRoles(code);
	}

	public void editRole(Role role) {
		this.roleMapper.editRole(role);
	}

	public int getChildCount(String code) {
		return this.roleMapper.getChildCount(code);
	}

	public void delRole(String id) {
		this.roleMapper.delRole(id);
	}

	public void delMenuRole(String id) {
		this.roleMapper.delMenuRole(id);
	}

	public void delUserRole(String id) {
		this.roleMapper.delUserRole(id);
	}

	public void delGroupRole(String id) {
		this.roleMapper.delGroupRole(id);
	}

	public List<Menu> getMenuTrees() {
		return this.roleMapper.getMenuTrees();
	}

	public List<User> getEnableUsers() {
		return this.roleMapper.getEnableUsers();
	}

	public List<Group> getGroupTrees() {
		return this.roleMapper.getGroupTrees();
	}

	public List<String> getGroupIdsByRoleId(String roleId) {
		return this.roleMapper.getGroupIdsByRoleId(roleId);
	}

	public void insertRoles(List<Role> roles) {
		this.roleMapper.insertRoles(roles);
	}

	public List<Role> getCascadeRoles(String code) {
		return this.roleMapper.getCascadeRoles(code);
	}

}
