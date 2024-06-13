package com.yihe.applet.api.service;

import java.util.Map;

import com.yihe.applet.api.domain.SleepRemindApp;

public interface SleepRemindAppService {
	int insertSelective(SleepRemindApp record);
	 int updateByPrimaryKeySelective(SleepRemindApp record);
	 int deleteByPrimaryKey(Integer id);
	 SleepRemindApp selectSleepRemindByParam(Map<String, Object> map);
	 SleepRemindApp selectBySleepRemindApp(String userCode);
}
