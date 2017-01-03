package com.mullet.backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mullet.backend.dao.GroupDao;
import com.mullet.backend.entity.Group;
import com.mullet.backend.entity.GroupRef;
import com.mullet.backend.entity.Role;
import com.mullet.backend.entity.User;
import com.mullet.backend.service.GroupService;
import com.mullet.backend.util.StringHelper;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupDao groupDao;

	@Override
	public synchronized String getCode(String parentId) {
		Group m = this.groupDao.getMaxCode(parentId);
		if (StringHelper.isNullOrBlank(m.getParentCode())) {
			m.setParentCode(StringHelper.ROOT_CODE);
		}
		if (StringHelper.isNullOrBlank(m.getCode())) {
			if(StringHelper.ROOT_CODE.equals(m.getParentCode())){
				m.setCode(StringHelper.FIRST_CODE);
			}else{
				m.setCode(m.getParentCode() + StringHelper.SUB_CODE);
			}
		} else {
			m.setCode(StringHelper.getNextCode(m.getCode()));	
		}
		return m.getCode();
	}

	@Override
	public List<Group> suggest(String name, String value) {
		 List<Group> list = this.groupDao.suggest(name, value);
		 return list;
	}

	@Override
	public List<Group> getGroups(String searchValue) {
		return this.groupDao.getGroups(searchValue);
	}

	@Override
	public int getExistCount(String id, String value) {
		 int count = this.groupDao.getExistCount(id, value);
		 return count;
	}

	@Override
	public void insertGroup(Group group) {
		 this.groupDao.insertGroup(group);
	}
	
	@Override
	public List<Group> getEditGroups(String code){
		return this.groupDao.getEditGroups(code);
	}
	
	@Override
	public List<Group> getChildGroups(String code){
		return this.groupDao.getChildGroups(code);
	}

	@Override
	public void editGroup(Group group) {
		this.groupDao.editGroup(group);
	}

	@Override
	public int getChildCount(String code){
		return this.groupDao.getChildCount(code);
	}

	@Override
	public void delGroup(String id) {
		this.groupDao.delGroup(id);
	}

	@Override
	public Group getGroupById(String groupId) {
		return this.groupDao.getGroupById(groupId);
	}

	@Override
	public void delGroupUser(String id) {
		 this.groupDao.delGroupUser(id);
	}

	@Override
	public void delGroupRole(String id) {
		this.groupDao.delGroupRole(id);
	}

	@Override
	public List<User> getEnableUsers() {
		return this.groupDao.getEnableUsers();
	}

	@Override
	public List<Role> getRoleTrees() {
		return this.groupDao.getRoleTrees();
	}

	@Override
	public List<String> getUserIdsByGroupId(String groupId) {
		return this.groupDao.getUserIdsByGroupId(groupId);
	}

	@Override
	public List<String> getRoleIdsByGroupId(String groupId) {
		return this.groupDao.getRoleIdsByGroupId(groupId);
	}

	@Override
	public void insertGroupRole(List<GroupRef> list) {
		this.groupDao.insertGroupRole(list);
	}

	@Override
	public void insertGroupUser(List<GroupRef> list) {
		this.groupDao.insertGroupUser(list);
	}

	@Override
	public void insertGroups(List<Group> groups) {
		// TODO Auto-generated method stub
		this.groupDao.insertGroups(groups);
	}

	@Override
	public List<Group> getCascadeGroups(String code) {
		// TODO Auto-generated method stub
		return this.groupDao.getCascadeGroups(code);
	}
	
}
