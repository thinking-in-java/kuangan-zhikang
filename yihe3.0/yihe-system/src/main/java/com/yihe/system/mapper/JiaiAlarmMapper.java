package com.yihe.system.mapper;

import com.yihe.system.domain.JiaiAlarm;

public interface JiaiAlarmMapper {
    int insert(JiaiAlarm record);

    int insertSelective(JiaiAlarm record);
}