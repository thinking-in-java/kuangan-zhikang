package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhMyLifeHabitAssessResultApp;

public interface YhMyLifeHabitAssessResultAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhMyLifeHabitAssessResultApp record);

    int insertSelective(YhMyLifeHabitAssessResultApp record);

    YhMyLifeHabitAssessResultApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhMyLifeHabitAssessResultApp record);

    int updateByPrimaryKey(YhMyLifeHabitAssessResultApp record);
}