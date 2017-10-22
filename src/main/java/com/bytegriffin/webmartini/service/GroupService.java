package com.bytegriffin.webmartini.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bytegriffin.webmartini.domain.Group;
import com.bytegriffin.webmartini.domain.GroupRef;
import com.bytegriffin.webmartini.domain.Role;
import com.bytegriffin.webmartini.domain.User;
import com.bytegriffin.webmartini.mapper.GroupMapper;
import com.bytegriffin.webmartini.util.StringHelper;

@Service
@Transactional(rollbackFor=Throwable.class) 
public class GroupService {

	@Autowired
	private GroupMapper groupMapper;

	public synchronized String getCode(String parentId) {
		Group m = this.groupMapper.getMaxCode(parentId);
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

	
	public List<Group> suggest(String name, String value) {
		 List<Group> list = this.groupMapper.suggest(name, value);
		 return list;
	}

	
	public List<Group> getGroups(String searchValue) {
		return this.groupMapper.getGroups(searchValue);
	}

	
	public int getExistCount(String id, String value) {
		 int count = this.groupMapper.getExistCount(id, value);
		 return count;
	}

	
	public void insertGroup(Group group) {
		 this.groupMapper.insertGroup(group);
	}
	
	
	public List<Group> getEditGroups(String code){
		return this.groupMapper.getEditGroups(code);
	}
	
	
	public List<Group> getChildGroups(String code){
		return this.groupMapper.getChildGroups(code);
	}

	
	public void editGroup(Group group) {
		this.groupMapper.editGroup(group);
	}

	
	public int getChildCount(String code){
		return this.groupMapper.getChildCount(code);
	}

	
	public void delGroup(String id) {
		this.groupMapper.delGroup(id);
	}

	
	public Group getGroupById(String groupId) {
		return this.groupMapper.getGroupById(groupId);
	}

	
	public void delGroupUser(String id) {
		 this.groupMapper.delGroupUser(id);
	}

	
	public void delGroupRole(String id) {
		this.groupMapper.delGroupRole(id);
	}

	
	public List<User> getEnableUsers() {
		return this.groupMapper.getEnableUsers();
	}

	
	public List<Role> getRoleTrees() {
		return this.groupMapper.getRoleTrees();
	}

	
	public List<String> getUserIdsByGroupId(String groupId) {
		return this.groupMapper.getUserIdsByGroupId(groupId);
	}

	
	public List<String> getRoleIdsByGroupId(String groupId) {
		return this.groupMapper.getRoleIdsByGroupId(groupId);
	}

	
	public void insertGroupRole(List<GroupRef> list) {
		this.groupMapper.insertGroupRole(list);
	}

	
	public void insertGroupUser(List<GroupRef> list) {
		this.groupMapper.insertGroupUser(list);
	}

	
	public void insertGroups(List<Group> groups) {
		this.groupMapper.insertGroups(groups);
	}

	
	public List<Group> getCascadeGroups(String code) {
		return this.groupMapper.getCascadeGroups(code);
	}
	
}
