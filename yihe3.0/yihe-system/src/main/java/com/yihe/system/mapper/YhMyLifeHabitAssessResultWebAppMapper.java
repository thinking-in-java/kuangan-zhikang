package com.yihe.system.mapper;

import com.yihe.system.domain.YhMyLifeHabitAssessResultWebApp;

public interface YhMyLifeHabitAssessResultWebAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhMyLifeHabitAssessResultWebApp record);

    int insertSelective(YhMyLifeHabitAssessResultWebApp record);

    YhMyLifeHabitAssessResultWebApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhMyLifeHabitAssessResultWebApp record);

    int updateByPrimaryKey(YhMyLifeHabitAssessResultWebApp record);
    
    YhMyLifeHabitAssessResultWebApp selectByResultAssess(Integer dictId);
}