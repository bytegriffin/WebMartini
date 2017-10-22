package com.bytegriffin.webmartini.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bytegriffin.webmartini.domain.UserMessage;
import com.bytegriffin.webmartini.mapper.UserMessageMapper;

@Service
@Transactional(rollbackFor = Throwable.class)
public class UserMessageService {

	@Autowired
	private UserMessageMapper userMessageMapper;

	public List<UserMessage> getUserMessagesByRecId(String userId, String status) {
		return userMessageMapper.getUserMessagesByRecId(userId, status);
	}

	public void insert(UserMessage userMessage) {
		userMessageMapper.insert(userMessage);
	}

	public void delete(List<String> ids) {
		userMessageMapper.delete(ids);
	}

	public List<UserMessage> getTopUserMessagesByRecId(String userId) {
		return userMessageMapper.getTopUserMessagesByRecId(userId);
	}

	public int getUnReadCountByRecId(String id) {
		return userMessageMapper.getUnReadCountByRecId(id);
	}

	public UserMessage getUserMessageById(String id) {
		return userMessageMapper.getUserMessageById(id);
	}

	public void read(String id) {
		userMessageMapper.read(id);
	}

	public List<UserMessage> getAllUserMessagesByRecId(String userId) {
		return userMessageMapper.getAllUserMessagesByRecId(userId);
	}

}
