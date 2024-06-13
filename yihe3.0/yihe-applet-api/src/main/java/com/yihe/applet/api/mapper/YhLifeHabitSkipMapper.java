package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhLifeHabitSkip;

public interface YhLifeHabitSkipMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhLifeHabitSkip record);

    int insertSelective(YhLifeHabitSkip record);

    YhLifeHabitSkip selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhLifeHabitSkip record);

    int updateByPrimaryKey(YhLifeHabitSkip record);
    
    int showskipCount(String userCode);
}