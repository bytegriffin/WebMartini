package com.bytegriffin.webmartini.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.bytegriffin.webmartini.domain.UserLog;

@Mapper
public interface UserLogMapper {

	void insert(UserLog userLog);

	List<UserLog> getUserLogs(@Param("searchValue") String searchValue,@Param("startTime") String startTime,@Param("endTime")  String endTime);

	List<UserLog> suggest(@Param("userName") String userName,@Param("searchValue") String searchValue);

	List<UserLog> getErrorUserLogs(@Param("searchValue") String searchValue,@Param("startTime") String startTime,@Param("endTime")  String endTime);

	List<UserLog> suggestError(@Param("userName") String userName,@Param("searchValue") String searchValue);

	UserLog getUserLogById(@Param("id") String id);

	List<UserLog> getRecentImportantEvent(@Param("id") String id);

	List<UserLog> getPVByUserId(@Param("id") String id);

}
