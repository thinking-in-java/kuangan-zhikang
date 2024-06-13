package com.yihe.system.mapper;

import com.yihe.system.domain.YhLifeHabitImprovementDict;
import com.yihe.system.domain.YhLifeHabitImprovementDictVojo;

import java.util.List;
import java.util.Map;

public interface YhLifeHabitImprovementDictMapper {
	String findHabitDictNewInfo(String usercode);
	
    int deleteByPrimaryKey(Integer id);

    int insert(YhLifeHabitImprovementDict record);

    int insertSelective(YhLifeHabitImprovementDict record);

    YhLifeHabitImprovementDict selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhLifeHabitImprovementDict record);

    int updateByPrimaryKey(YhLifeHabitImprovementDict record);
    
    List<YhLifeHabitImprovementDictVojo> sleepbyImprovement(Map<String, Object> map);
    
    List<YhLifeHabitImprovementDictVojo> sleepbyImprovementCount(Map<String, Object> map);
    
    int ShowByHabitCode(String userCode);
    
    List<YhLifeHabitImprovementDictVojo> ShowHabitrecord(Map<String, Object> map);
    
    int ShowHabitrecordCount(Map<String, Object> map);
    
    List<YhLifeHabitImprovementDict> ShowPersonageHabit(Map<String, Object> map);
    
    int ShowPersonageHabitCount(Map<String, Object> map);

    int selectroletype(String usercode);
}