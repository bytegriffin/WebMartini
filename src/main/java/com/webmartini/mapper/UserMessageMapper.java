package com.webmartini.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.webmartini.domain.UserMessage;

@Mapper
public interface UserMessageMapper {
	
	List<UserMessage> getAllUserMessagesByRecId(@Param("recId") String userId);

	List<UserMessage> getUserMessagesByRecId(@Param("recId") String userId, @Param("status") String status);

	void insert(UserMessage userMessage);

	void delete(List<String> ids);

	List<UserMessage> getTopUserMessagesByRecId(@Param("recId") String userId);
	
	int getUnReadCountByRecId(@Param("recId") String id);
	
	UserMessage getUserMessageById(@Param("id") String id);
	
	void read(@Param("id") String id);

}
