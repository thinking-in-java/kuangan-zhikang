package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YhUserHabitGuide;

public interface YhUserHabitGuideMapper {
    int deleteByPrimaryKey(Integer habitGuideId);

    int insert(YhUserHabitGuide record);

    int insertSelective(YhUserHabitGuide record);

    YhUserHabitGuide selectByPrimaryKey(Integer habitGuideId);

    int updateByPrimaryKeySelective(YhUserHabitGuide record);

    int updateByPrimaryKey(YhUserHabitGuide record);
    
    List<YhUserHabitGuide> selectHabitlyze(Map<String, Object> map);
    
    int selectHabitlyzeConut(Map<String, Object> map);
}