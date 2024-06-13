package com.yihe.applet.api.service;

import java.util.List;

import com.yihe.applet.api.domain.YhUserChatRecord;

public interface YhUserChatRecordService {
	String findDoctorIdByUserCode(String usercode);
	List<YhUserChatRecord> findInfoList(List<String> id);
	int insertSelective(YhUserChatRecord record);
	
	/**
	 * 修改已读状态
	 * @param usercode
	 * @param tousercode
	 * @return
	 */
	int modifyRead1(String usercode,String tousercode);
	
	String findUserGroupByInfo(String usercode);
}
