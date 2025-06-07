package com.yihe.applet.api.service;

import java.util.List;

import com.yihe.applet.api.domain.DoctorXcxHomePageChat;
import com.yihe.applet.api.domain.YhUserChatRecord;
import com.yihe.applet.api.domain.YhUserChatRecordVOJO;

public interface YhUserChatRecordService {
	String findDoctorIdByUserCode(String usercode);
	List<YhUserChatRecord> findInfoList(List<String> id, int pagenum, int pagesize);
	int insertSelective(YhUserChatRecord record);
	
	/**
	 * 修改已读状态
	 * @param usercode
	 * @param tousercode
	 * @return
	 */
	int modifyRead1(String usercode,String tousercode);
	
	String findUserGroupByInfo(String usercode);

	/**
	 * 医生端首页聊天列表数据
	 *
	 * @param doctor
	 * @param limit
	 * @return
	 */
	List<DoctorXcxHomePageChat> latestChat(String doctor, int limit);
	/***
	 * 查询医生端私信未读数量
	 * @param doctor
	 * @return
	 */
	int doctorUnreadNum(String doctor);

	/**
	 * 封装历史数据
	 */
	List<YhUserChatRecordVOJO> getChatInfo(List<String> id, int pagenum, int pagesize);

	/**
	 * 插入聊天记录并发送离线消息
	 */
	int addChatMessage(YhUserChatRecord record);
}
