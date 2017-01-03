package com.mullet.backend.service;

import java.util.List;

import com.mullet.backend.entity.Group;
import com.mullet.backend.entity.GroupRef;
import com.mullet.backend.entity.Role;
import com.mullet.backend.entity.User;

public interface GroupService {
	
	String getCode(String parentId);
	
	List<Group> suggest(String name, String value);

	List<Group> getGroups(String searchValue);
	
	List<Group> getCascadeGroups(String code);
	
	int getExistCount(String id, String value);

	void insertGroup(Group group);
	
	void insertGroups(List<Group> groups);
	
	Group getGroupById(String groupId);
	
	List<Group> getEditGroups(String code);
	
	List<Group> getChildGroups(String code);
	
	void editGroup(Group group);
	
	int getChildCount(String code);

	void delGroup(String id);
	
	void delGroupUser(String id);

	void delGroupRole(String id);

	List<User> getEnableUsers();
	
	List<Role> getRoleTrees();
	
	List<String> getUserIdsByGroupId(String groupId);

	List<String> getRoleIdsByGroupId(String groupId);

	void insertGroupRole(List<GroupRef> list);

	void insertGroupUser(List<GroupRef> list);

}
