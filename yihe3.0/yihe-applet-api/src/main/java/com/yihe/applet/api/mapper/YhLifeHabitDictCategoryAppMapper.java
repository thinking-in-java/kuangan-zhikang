package com.yihe.applet.api.mapper;

import java.util.List;

import com.yihe.applet.api.domain.YhLifeHabitDictCategoryApp;

public interface YhLifeHabitDictCategoryAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhLifeHabitDictCategoryApp record);

    int insertSelective(YhLifeHabitDictCategoryApp record);

    YhLifeHabitDictCategoryApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhLifeHabitDictCategoryApp record);

    int updateByPrimaryKey(YhLifeHabitDictCategoryApp record);
    
    List<YhLifeHabitDictCategoryApp> showheading();
}