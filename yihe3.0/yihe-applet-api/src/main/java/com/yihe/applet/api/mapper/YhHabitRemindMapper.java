package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhHabitRemind;

public interface YhHabitRemindMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteremind(YhHabitRemind remind);
    
    int insert(YhHabitRemind record);

    int insertSelective(YhHabitRemind record);

    YhHabitRemind selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhHabitRemind record);

    int updateByPrimaryKey(YhHabitRemind record);
    
    int showremindCount(String userCode);
}