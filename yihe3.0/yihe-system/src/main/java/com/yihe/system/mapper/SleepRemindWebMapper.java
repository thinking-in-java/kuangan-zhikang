package com.yihe.system.mapper;

import java.util.Map;


import com.yihe.system.domain.SleepRemindWeb;

public interface SleepRemindWebMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SleepRemindWeb record);

    int insertSelective(SleepRemindWeb record);

    SleepRemindWeb selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SleepRemindWeb record);

    int updateByPrimaryKey(SleepRemindWeb record);
    
    SleepRemindWeb selectSleepRemindByParam(Map<String, Object> map);
}