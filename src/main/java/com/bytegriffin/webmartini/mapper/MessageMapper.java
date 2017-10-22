package com.bytegriffin.webmartini.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bytegriffin.webmartini.domain.Message;

@Mapper
public interface MessageMapper {

	List<Message> getMessages(@Param("status") String status);

	int insert(Message message);
	
	Message getMessageById(@Param("id") String id);
	
	void delete(List<String> ids);
	
	void publish(@Param("id") String id);
}
