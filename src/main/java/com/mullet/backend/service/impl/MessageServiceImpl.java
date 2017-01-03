package com.mullet.backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mullet.backend.dao.MessageDao;
import com.mullet.backend.entity.Message;
import com.mullet.backend.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService{

	@Autowired
	private MessageDao messageDao;

	@Override
	public List<Message> getMessages(String status) {
		return messageDao.getMessages(status);
	}

	@Override
	public int insert(Message message) {
		 messageDao.insert(message);
	  return	Integer.valueOf(message.getId());
	}

	@Override
	public Message getMessageById(String id) {
		return messageDao.getMessageById(id);
	}

	@Override
	public void delete(List<String> ids) {
		// TODO Auto-generated method stub
		messageDao.delete(ids);
	}

	@Override
	public void publish(String id) {
		// TODO Auto-generated method stub
		messageDao.publish(id);
	}

	@Override
	public void update(Message message) {
		// TODO Auto-generated method stub
		
	}

}
