package com.mullet.backend.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mullet.backend.dao.GroupDao;
import com.mullet.backend.dao.RoleDao;
import com.mullet.backend.dao.UserDao;
import com.mullet.backend.entity.Group;
import com.mullet.backend.entity.Role;
import com.mullet.backend.entity.User;
import com.mullet.backend.entity.UserRef;
import com.mullet.backend.service.UserService;
import com.mullet.backend.util.DateUtil;
import com.mullet.backend.util.StringHelper;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private RoleDao roleDao;

	@Override
	public User getUserByUserName(String value) {
		User user = userDao.getUserByUserName(value);
		if(user != null){
			List<Group> glist = groupDao.getGroupsByUserId(user.getId());
			Set<String> groupNames = new HashSet<String>();
			for(Group g : glist){
				groupNames.add(g.getName());
			}
			user.setGroupNames(groupNames);
			user.setGroupNamesStr(StringHelper.SetToString(groupNames));
			
			List<Role> rlist = roleDao.getRolesByUserId(user.getId());
			Set<String> roleNames = new HashSet<String>();
			for(Role r : rlist){
				roleNames.add(r.getName());
			}
			user.setRoleNames(roleNames);
			user.setRoleNamesStr(StringHelper.SetToString(roleNames));
		}
		return user;
	}
	
	
	@Override
	public void editUserStatus(String id, String status){
	  this.userDao.editUserStatus(id, status);
	}

	@Override
	public User getUserById(String userId) {
		return this.userDao.getUserById(userId);
	}

	@Override
	public List<User> getUsers(String name, String value) {
		return this.userDao.getUsers(name, value);
	}

	@Override
	public void insertUser(User user) {
		this.userDao.insertUser(user);
	}

	@Override
	public void editUser(User user) {
		this.userDao.editUser(user);
	}
	
	@Override
	public void delUser(List<String> ids) {
		this.userDao.delUser(ids);
	}

	@Override
	public int getExistCount(String name, String value, String id) {
		int count = this.userDao.getExistCount(name, value, id);
		return count;
	}

	@Override
	public List<User> suggest(String name, String value) {
		List<User> list = this.userDao.suggest(name, value);
		return list;
	}

	@Override
	public void delUserRole(List<String> ids) {
		this.userDao.delUserRole(ids);
	}

	@Override
	public void insertUserRole(List<UserRef> list) {
		this.userDao.insertUserRole(list);
	}

	@Override
	public void insertGroupUser(List<UserRef> list) {
		this.userDao.insertGroupUser(list);
	}

	@Override
	public List<String> getGroupIdsByUserId(String userId) {
		return this.userDao.getGroupIdsByUserId(userId);
	}

	@Override
	public List<String> getRoleIdsByUserId(String userId) {
		return this.userDao.getRoleIdsByUserId(userId);
	}

	@Override
	public void delUserRoleByUserId(String id) {
		this.userDao.delUserRoleByUserId(id);
	}

	@Override
	public void delGroupUserByUserId(String id) {
		this.userDao.delGroupUserByUserId(id);
	}

	@Override
	public User getUserByLoginName(String loginName) {
		return this.userDao.getUserByLoginName(loginName);
	}

	@Override
	public User getUserByLoginEmail(String email) {
		return this.userDao.getUserByLoginEmail(email);
	}

	@Override
	public List<String> getPermissions(String userid) {
		return this.userDao.getPermissions(userid);
	}

	@Override
	public void updateUserAvatar(String userId, String avatar) {
			this.userDao.updateUserAvatar(userId, avatar);
	}

	@Override
	public List<String> getGroupNamesByUserId(String userId) {
		// TODO Auto-generated method stub
		return this.userDao.getGroupNamesByUserId(userId);
	}

	@Override
	public List<String> getRoleNamesByUserId(String userId) {
		// TODO Auto-generated method stub
		return this.userDao.getRoleNamesByUserId(userId);
	}

	@Override
	public void editUserProfile(User user) {
		// TODO Auto-generated method stub
	  this.userDao.editUserProfile(user);
	}

	@Override
	public void editUserPassword(String userId, String password) {
		// TODO Auto-generated method stub
		this.userDao.editUserPassword(userId, password);
	}

	@Override
	public void insertUsers(List<User> userlist) {
		// TODO Auto-generated method stub
		this.userDao.insertUsers(userlist);
	}


	@Override
	public void delUserGroup(List<String> ids) {
		// TODO Auto-generated method stub
		this.userDao.delUserGroup(ids);
	}


	@Override
	public void updateUserLoginStatus(String id,String loginIP,int loginCount) {
		User user = new User();
		user.setId(id);
		String loginTime = DateUtil.getCurrentDayTime();
		user.setLoginTime(DateUtil.strToDate(loginTime));
		user.setLoginIP(loginIP);
		user.setLoginCount(loginCount+1);
		this.userDao.updateUserLoginStatus(user);
	}

}