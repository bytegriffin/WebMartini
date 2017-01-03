package com.mullet.backend.service;

import java.util.List;

import com.mullet.backend.entity.Group;
import com.mullet.backend.entity.Menu;
import com.mullet.backend.entity.Role;
import com.mullet.backend.entity.RoleRef;
import com.mullet.backend.entity.User;

public interface RoleService {
	
	String getCode(String parentId);

	List<Role> suggest(String name, String value);

	List<Menu> getMenuTrees();

	List<User> getEnableUsers();

	List<Group> getGroupTrees();

	List<Role> getRoles(String searchValue);
	
	List<Role> getCascadeRoles(String code);

	List<String> getMenuIdsByRoleId(String roleId);

	List<String> getUserIdsByRoleId(String roleId);

	List<String> getGroupIdsByRoleId(String roleId);

	Role getRoleById(String roleId);

	void insertMenuRole(List<RoleRef> list);

	void insertUserRole(List<RoleRef> list);
	
	void insertGroupRole(List<RoleRef> list);

	void insertRole(Role role);
	
	void insertRoles(List<Role> roles);
	
	List<Role> getChildRoles(String code);

	List<Role> getEditRoles(String code);
	
	void editRole(Role role);
	
	int getChildCount(String code);

	void delRole(String id);

	void delMenuRole(String id);

	void delUserRole(String id);
	
	void delGroupRole(String id);

	int getExistCount(String id, String value);

}
