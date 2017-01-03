package com.mullet.backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mullet.backend.dao.UserMessageDao;
import com.mullet.backend.entity.UserMessage;
import com.mullet.backend.service.UserMessageService;

@Service
public class UserMessageServiceImpl implements UserMessageService{

	@Autowired
	private UserMessageDao userMessageDao;

	@Override
	public List<UserMessage> getUserMessagesByRecId(String userId, String status) {
		return userMessageDao.getUserMessagesByRecId(userId, status);
	}

	@Override
	public void insert(UserMessage userMessage) {
		// TODO Auto-generated method stub
		userMessageDao.insert(userMessage);
	}

	@Override
	public void delete(List<String> ids) {
		// TODO Auto-generated method stub
		userMessageDao.delete(ids);
	}

	@Override
	public List<UserMessage> getTopUserMessagesByRecId(String userId) {
		// TODO Auto-generated method stub
		return userMessageDao.getTopUserMessagesByRecId(userId);
	}

	@Override
	public int getUnReadCountByRecId(String id) {
		return userMessageDao.getUnReadCountByRecId(id);
	}

	@Override
	public UserMessage getUserMessageById(String id) {
		// TODO Auto-generated method stub
		return userMessageDao.getUserMessageById(id);
	}

	@Override
	public void read(String id) {
		// TODO Auto-generated method stub
		userMessageDao.read(id);
	}

	@Override
	public List<UserMessage> getAllUserMessagesByRecId(String userId) {
		// TODO Auto-generated method stub
		return userMessageDao.getAllUserMessagesByRecId(userId);
	}

}
