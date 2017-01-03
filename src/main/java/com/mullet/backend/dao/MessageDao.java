package com.mullet.backend.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mullet.backend.entity.Message;


public interface MessageDao {

	List<Message> getMessages(@Param("status") String status);

	int insert(Message message);
	
	Message getMessageById(@Param("id") String id);
	
	void delete(List<String> ids);
	
	void publish(@Param("id") String id);
}
