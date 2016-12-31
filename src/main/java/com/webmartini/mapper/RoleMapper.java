package com.webmartini.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.webmartini.domain.Group;
import com.webmartini.domain.Menu;
import com.webmartini.domain.Role;
import com.webmartini.domain.RoleRef;
import com.webmartini.domain.User;

@Mapper
public interface RoleMapper {
	
	Role getMaxCode(@Param("parentId") String parentId);

	List<Menu> getMenuTrees();

	List<User> getEnableUsers();
	
	List<Group> getGroupTrees();

	List<Role> suggest(String name, String value);

	List<Role> getRoles(@Param("searchValue") String searchValue);
	
	List<Role> getCascadeRoles(@Param("code") String code);
	
	
	List<Role> getRolesByUserId(@Param("userId") String userId);

	Role getRoleById(String roleId);

	List<String> getMenuIdsByRoleId(@Param("roleId") String roleId);
	
	List<String> getUserIdsByRoleId(@Param("roleId") String roleId);
	
	List<String> getGroupIdsByRoleId(@Param("roleId") String roleId);

	void insertMenuRole(List<RoleRef> list);

	void insertUserRole(List<RoleRef> list);

	void insertGroupRole(List<RoleRef> list);

	void insertRole(Role role);

	void insertRoles(List<Role> roles);

	List<Role> getEditRoles(@Param("code") String code);
	
	List<Role> getChildRoles(@Param("code") String code);

	void editRole(Role role);

	int getChildCount(@Param("code") String code);

	void delRole(@Param("id") String id);

	void delMenuRole(@Param("id") String id);

	void delUserRole(@Param("id") String id);
	
	void delGroupRole(@Param("id") String id);

	int getExistCount(String id, String value);

}
