package com.webmartini.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.webmartini.domain.Group;
import com.webmartini.domain.GroupRef;
import com.webmartini.domain.Role;
import com.webmartini.domain.User;

@Mapper
public interface GroupMapper {
	
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
