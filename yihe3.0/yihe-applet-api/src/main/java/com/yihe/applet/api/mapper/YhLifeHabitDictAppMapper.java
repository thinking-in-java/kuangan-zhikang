package com.yihe.applet.api.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.applet.api.domain.YhLifeHabitDictApp;

public interface YhLifeHabitDictAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhLifeHabitDictApp record);

    int insertSelective(YhLifeHabitDictApp record);

    YhLifeHabitDictApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhLifeHabitDictApp record);

    int updateByPrimaryKey(YhLifeHabitDictApp record);
    
    List<YhLifeHabitDictApp> selectbytype(Map<String, Object> map);
}