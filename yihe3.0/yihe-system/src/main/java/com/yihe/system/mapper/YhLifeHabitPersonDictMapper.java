package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YhLifeHabitPersonDict;

public interface YhLifeHabitPersonDictMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhLifeHabitPersonDict record);

    int insertSelective(YhLifeHabitPersonDict record);

    YhLifeHabitPersonDict selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhLifeHabitPersonDict record);

    int updateByPrimaryKey(YhLifeHabitPersonDict record);
    
    List<YhLifeHabitPersonDict> showPersonHabit(Map<String, Object> map);
    
    int showPersonHabitCount(Map<String, Object> map);
    
    List<YhLifeHabitPersonDict> selectByPersoncode(String userCode);
    
    int seelctByHabitCodeCount(Map<String, Object> map);
    
    List<YhLifeHabitPersonDict> selectBynullDict(String userCode);
    
    List<YhLifeHabitPersonDict> selectBynullDict1(Map<String, Object> map);
    
    List<YhLifeHabitPersonDict> selectBydict(Map<String, Object> map);
    
    List<YhLifeHabitPersonDict> showdictdate(Map<String, Object> map);
    
    YhLifeHabitPersonDict selectByDictStandby(Map<String, Object> map);
}