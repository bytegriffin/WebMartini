package com.mullet.backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mullet.backend.dao.UserLogDao;
import com.mullet.backend.entity.UserLog;
import com.mullet.backend.service.UserLogService;

@Service
public class UserLogServiceImpl implements UserLogService{
	
	@Autowired
	private UserLogDao userLogDao;

	@Override
	public void insert(UserLog userLog) {
		// TODO Auto-generated method stub
		userLogDao.insert(userLog);
	}

	@Override
	public List<UserLog> getUserLogs(String searchValue,String startTime, String endTime) {
		// TODO Auto-generated method stub
		return userLogDao.getUserLogs(searchValue, startTime, endTime);
	}

	@Override
	public List<UserLog> suggest(String name, String value) {
		// TODO Auto-generated method stub
		return userLogDao.suggest(name, value);
	}

	@Override
	public List<UserLog> getErrorUserLogs(String searchValue,String startTime, String endTime) {
		// TODO Auto-generated method stub
		return userLogDao.getErrorUserLogs(searchValue, startTime, endTime);
	}

	@Override
	public List<UserLog> suggestError(String name, String value) {
		// TODO Auto-generated method stub
		return userLogDao.suggestError(name, value);
	}

	@Override
	public UserLog getUserLogById(String id) {
		// TODO Auto-generated method stub
		return userLogDao.getUserLogById(id);
	}

	@Override
	public List<UserLog> getRecentImportantEvent(String userid) {
		// TODO Auto-generated method stub
		return userLogDao.getRecentImportantEvent(userid);
	}

	@Override
	public List<UserLog> getPVByUserId(String id) {
		// TODO Auto-generated method stub
		return userLogDao.getPVByUserId(id);
	}


}
