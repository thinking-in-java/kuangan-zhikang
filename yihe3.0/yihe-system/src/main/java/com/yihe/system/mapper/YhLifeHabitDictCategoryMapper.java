package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YhLifeHabitDictCategory;

public interface YhLifeHabitDictCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhLifeHabitDictCategory record);

    int insertSelective(YhLifeHabitDictCategory record);

    YhLifeHabitDictCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhLifeHabitDictCategory record);

    int updateByPrimaryKey(YhLifeHabitDictCategory record);
    
    List<YhLifeHabitDictCategory> ShowselectByPrimaryKey();
    
    int selctCategoryName(Map<String, Object> map);
    
}