package com.yihe.applet.api.mapper;

import java.util.Map;

import com.yihe.applet.api.domain.SleepRemindApp;

public interface SleepRemindAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SleepRemindApp record);

    int insertSelective(SleepRemindApp record);

    SleepRemindApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SleepRemindApp record);

    int updateByPrimaryKey(SleepRemindApp record);
    SleepRemindApp selectBySleepRemindApp(String userCode);
    SleepRemindApp selectSleepRemindByParam(Map<String, Object> map);
}