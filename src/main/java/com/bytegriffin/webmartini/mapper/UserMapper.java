package com.bytegriffin.webmartini.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bytegriffin.webmartini.domain.User;
import com.bytegriffin.webmartini.domain.UserRef;

@Mapper
public interface UserMapper {


	List<String> getPermissions(@Param("id") String id);

	List<User> suggest(String name, String value);

	int getExistCount(String name, String value, String id);

	User getUserById(@Param("id") String userId);

	User getUserByUserName(@Param("username") String value);

	User getUserByLoginName(@Param("loginName") String loginName);

	User getUserByLoginEmail(@Param("email") String email);

	List<User> getUsers(String name, String value);

	int insertUser(User user);

	void insertUsers(List<User> userlist);

	void insertUserRole(List<UserRef> list);

	void insertGroupUser(List<UserRef> list);

	List<String> getGroupNamesByUserId(@Param("userId") String userId);

	List<String> getRoleNamesByUserId(@Param("userId") String userId);

	List<String> getGroupIdsByUserId(@Param("userId") String userId);

	List<String> getRoleIdsByUserId(@Param("userId") String userId);

	void delUserRoleByUserId(@Param("id") String id);

	void delGroupUserByUserId(@Param("id") String id);

	void updateUserAvatar(@Param("userId") String userId, @Param("avatar") String avatar);

	void editUser(User user);

	void editUserProfile(User user);

	void delUser(List<String> ids);

	void delUserRole(List<String> ids);

	void delUserGroup(List<String> ids);

	void editUserStatus(String id, String status);

	void editUserPassword(@Param("userId") String userId, @Param("password") String password);

	void updateUserLoginStatus(User user);

}
