package com.mullet.backend.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mullet.backend.entity.Group;
import com.mullet.backend.entity.GroupRef;
import com.mullet.backend.entity.Role;
import com.mullet.backend.entity.User;

public interface GroupDao {
	
	Group getMaxCode(@Param("parentId") String parentId);

	List<Group> suggest(String name, String value);

	List<Group> getGroups(@Param("searchValue") String searchValue);
	
	List<Group> getCascadeGroups(@Param("code") String code);
	
	List<Group> getGroupsByUserId(@Param("userId") String userId);

	void insertGroup(Group group);
	
	void insertGroups(List<Group> groups);

	List<Group> getEditGroups(@Param("code") String code);
	
	List<Group> getChildGroups(@Param("code") String code);
	
	void editGroup(Group group);
	
	int getChildCount(@Param("code") String code);

	void delGroup(@Param("id") String ids);
	
	void delGroupUser(@Param("id") String ids);

	void delGroupRole(@Param("id") String ids);

	Group getGroupById(String groupId);

	int getExistCount(String id, String value);

	List<User> getEnableUsers();
	
	List<Role> getRoleTrees();
	
	List<String> getUserIdsByGroupId(@Param("groupId") String groupId);

	List<String> getRoleIdsByGroupId(@Param("groupId") String groupId);

	void insertGroupRole(List<GroupRef> list);

	void insertGroupUser(List<GroupRef> list);

}
