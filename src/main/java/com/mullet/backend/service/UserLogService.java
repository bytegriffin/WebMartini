package com.mullet.backend.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mullet.backend.entity.UserLog;

public interface UserLogService {

	void insert(UserLog userLog);

	List<UserLog> getUserLogs(String searchValue,String startTime, String endTime);

	List<UserLog> suggest(String name, String value);

	List<UserLog> getErrorUserLogs(String searchValue,String startTime, String endTime);

	List<UserLog> suggestError(String name, String value);

	UserLog getUserLogById(String id);

	List<UserLog> getRecentImportantEvent(String userid);

	List<UserLog> getPVByUserId(@Param("id") String id);

}
