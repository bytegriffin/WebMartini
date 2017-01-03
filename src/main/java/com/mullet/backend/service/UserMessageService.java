package com.mullet.backend.service;

import java.util.List;

import com.mullet.backend.entity.UserMessage;

public interface UserMessageService {
	
	List<UserMessage> getAllUserMessagesByRecId(String userId);
	
	List<UserMessage> getUserMessagesByRecId(String userId, String status);

	void insert(UserMessage userMessage);
	
	void delete(List<String> ids);
	
	List<UserMessage> getTopUserMessagesByRecId(String userId);

	int getUnReadCountByRecId(String id);
	
	UserMessage getUserMessageById(String id);
	
	void read(String id);
}
