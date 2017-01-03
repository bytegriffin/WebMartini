package com.mullet.backend.service;

import java.util.List;

import com.mullet.backend.entity.Message;

public interface MessageService {

	List<Message> getMessages(String status);

	int insert(Message message);

	void update(Message message);

	Message getMessageById(String id);

	void delete(List<String> ids);

	void publish(String id);

}
