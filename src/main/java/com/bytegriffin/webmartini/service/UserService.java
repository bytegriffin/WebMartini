package com.bytegriffin.webmartini.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bytegriffin.webmartini.domain.Group;
import com.bytegriffin.webmartini.domain.Role;
import com.bytegriffin.webmartini.domain.User;
import com.bytegriffin.webmartini.domain.UserRef;
import com.bytegriffin.webmartini.mapper.GroupMapper;
import com.bytegriffin.webmartini.mapper.RoleMapper;
import com.bytegriffin.webmartini.mapper.UserMapper;
import com.bytegriffin.webmartini.util.DateUtil;
import com.bytegriffin.webmartini.util.StringHelper;

@Service
@Transactional(rollbackFor = Throwable.class)
public class UserService {

	@Autowired
	private UserMapper userDao;

	@Autowired
	private GroupMapper groupDao;

	@Autowired
	private RoleMapper roleDao;
	
	public User getUserByUserName(String value) {
		User user = userDao.getUserByUserName(value);
		if (user != null) {
			List<Group> glist = groupDao.getGroupsByUserId(user.getId());
			Set<String> groupNames = new HashSet<String>();
			for (Group g : glist) {
				groupNames.add(g.getName());
			}
			user.setGroupNames(groupNames);
			user.setGroupNamesStr(StringHelper.SetToString(groupNames));

			List<Role> rlist = roleDao.getRolesByUserId(user.getId());
			Set<String> roleNames = new HashSet<String>();
			for (Role r : rlist) {
				roleNames.add(r.getName());
			}
			user.setRoleNames(roleNames);
			user.setRoleNamesStr(StringHelper.SetToString(roleNames));
		}
		return user;
	}

	public void editUserStatus(String id, String status) {
		this.userDao.editUserStatus(id, status);
	}

	public User getUserById(String userId) {
		return this.userDao.getUserById(userId);
	}

	public List<User> getUsers(String name, String value) {
		return this.userDao.getUsers(name, value);
	}

	public void insertUser(User user) {
		this.userDao.insertUser(user);
	}

	public void editUser(User user) {
		this.userDao.editUser(user);
	}

	public void delUser(List<String> ids) {
		this.userDao.delUser(ids);
	}

	public int getExistCount(String name, String value, String id) {
		int count = this.userDao.getExistCount(name, value, id);
		return count;
	}

	public List<User> suggest(String name, String value) {
		List<User> list = this.userDao.suggest(name, value);
		return list;
	}

	public void delUserRole(List<String> ids) {
		this.userDao.delUserRole(ids);
	}

	public void insertUserRole(List<UserRef> list) {
		this.userDao.insertUserRole(list);
	}

	public void insertGroupUser(List<UserRef> list) {
		this.userDao.insertGroupUser(list);
	}

	public List<String> getGroupIdsByUserId(String userId) {
		return this.userDao.getGroupIdsByUserId(userId);
	}

	public List<String> getRoleIdsByUserId(String userId) {
		return this.userDao.getRoleIdsByUserId(userId);
	}

	public void delUserRoleByUserId(String id) {
		this.userDao.delUserRoleByUserId(id);
	}

	public void delGroupUserByUserId(String id) {
		this.userDao.delGroupUserByUserId(id);
	}

	public User getUserByLoginName(String loginName) {
		return this.userDao.getUserByLoginName(loginName);
	}

	public User getUserByLoginEmail(String email) {
		return this.userDao.getUserByLoginEmail(email);
	}

	public List<String> getPermissions(String userid) {
		return this.userDao.getPermissions(userid);
	}

	public void updateUserAvatar(String userId, String avatar) {
		this.userDao.updateUserAvatar(userId, avatar);
	}

	public List<String> getGroupNamesByUserId(String userId) {
		return this.userDao.getGroupNamesByUserId(userId);
	}

	public List<String> getRoleNamesByUserId(String userId) {
		return this.userDao.getRoleNamesByUserId(userId);
	}

	public void editUserProfile(User user) {
		this.userDao.editUserProfile(user);
	}

	public void editUserPassword(String userId, String password) {
		this.userDao.editUserPassword(userId, password);
	}

	public void insertUsers(List<User> userlist) {
		this.userDao.insertUsers(userlist);
	}

	public void delUserGroup(List<String> ids) {
		this.userDao.delUserGroup(ids);
	}

	public void updateUserLoginStatus(String id, String loginIP, int loginCount) {
		User user = new User();
		user.setId(id);
		String loginTime = DateUtil.getCurrentDayTime();
		user.setLoginTime(DateUtil.strToDate(loginTime));
		user.setLoginIP(loginIP);
		user.setLoginCount(loginCount + 1);
		this.userDao.updateUserLoginStatus(user);
	}

}
