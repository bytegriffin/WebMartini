package com.webmartini.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webmartini.domain.Message;
import com.webmartini.mapper.MessageMapper;

@Service
@Transactional(rollbackFor = Throwable.class)
public class MessageService {

	@Autowired
	private MessageMapper messageMapper;

	public List<Message> getMessages(String status) {
		return messageMapper.getMessages(status);
	}

	public int insert(Message message) {
		messageMapper.insert(message);
		return Integer.valueOf(message.getId());
	}

	public Message getMessageById(String id) {
		return messageMapper.getMessageById(id);
	}

	public void delete(List<String> ids) {
		messageMapper.delete(ids);
	}

	public void publish(String id) {
		messageMapper.publish(id);
	}
}
