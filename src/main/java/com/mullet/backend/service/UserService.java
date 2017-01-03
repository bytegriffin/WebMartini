package com.mullet.backend.service;

import java.util.List;

import com.mullet.backend.entity.User;
import com.mullet.backend.entity.UserRef;

public interface UserService {
	
	/**
	 *    获取当前用户权限菜单
	 * @param userid
	 * @return
	 */
	List<String> getPermissions(String userid);

	List<User> suggest(String name, String value);

	int getExistCount(String name, String value, String id);

	User getUserById(String userId);
	
	/**
	   *  根据登陆时用户填写的参数（比如loginName，email或者其它） <br />
	   *  动态判断获取相应的用户      
	 * @param param
	 * @return
	 */
	User getUserByUserName(String param);//username = loginName or email or other...

	User getUserByLoginName(String loginName);

	User getUserByLoginEmail(String email);

	List<User> getUsers(String name, String value);

	void insertUser(User user);
	
	void insertUsers(List<User> userlist);

	void insertUserRole(List<UserRef> list);

	void insertGroupUser(List<UserRef> list);
	
	List<String> getGroupNamesByUserId(String userId);
	
	List<String> getRoleNamesByUserId(String userId);

	List<String> getGroupIdsByUserId(String userId);

	List<String> getRoleIdsByUserId(String userId);
	
	void delUserRoleByUserId(String id);

	void delGroupUserByUserId(String id);

	void editUser(User user);
	
	void editUserProfile(User user);
	
	void editUserPassword(String userId, String password);
	
	void updateUserAvatar(String userId,String avatar);

	void delUser(List<String> ids);

	void delUserRole(List<String> ids);
	
	void delUserGroup(List<String> ids);

	void editUserStatus(String id, String status);
	
	void updateUserLoginStatus(String id,String loginIP,int loginCount);

}
