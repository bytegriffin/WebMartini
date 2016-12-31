package com.webmartini.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webmartini.domain.UserLog;
import com.webmartini.mapper.UserLogMapper;

@Service
@Transactional(rollbackFor=Throwable.class) 
public class UserLogService{


	@Autowired
	private UserLogMapper userLogMapper;

	
	public void insert(UserLog userLog) {		
		userLogMapper.insert(userLog);
	}

	
	public List<UserLog> getUserLogs(String searchValue,String startTime, String endTime) {
		
		return userLogMapper.getUserLogs(searchValue, startTime, endTime);
	}

	
	public List<UserLog> suggest(String name, String value) {
		
		return userLogMapper.suggest(name, value);
	}

	
	public List<UserLog> getErrorUserLogs(String searchValue,String startTime, String endTime) {
		
		return userLogMapper.getErrorUserLogs(searchValue, startTime, endTime);
	}

	
	public List<UserLog> suggestError(String name, String value) {
		
		return userLogMapper.suggestError(name, value);
	}

	
	public UserLog getUserLogById(String id) {
		
		return userLogMapper.getUserLogById(id);
	}

	
	public List<UserLog> getRecentImportantEvent(String userid) {
		
		return userLogMapper.getRecentImportantEvent(userid);
	}

	
	public List<UserLog> getPVByUserId(String id) {
		
		return userLogMapper.getPVByUserId(id);
	}
}
