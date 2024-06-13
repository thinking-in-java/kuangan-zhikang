package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhLifeHabitRetroactive;

public interface YhLifeHabitRetroactiveMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhLifeHabitRetroactive record);

    int insertSelective(YhLifeHabitRetroactive record);

    YhLifeHabitRetroactive selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhLifeHabitRetroactive record);

    int updateByPrimaryKey(YhLifeHabitRetroactive record);
    
    YhLifeHabitRetroactive showRetroactivecode(String userCode);
}