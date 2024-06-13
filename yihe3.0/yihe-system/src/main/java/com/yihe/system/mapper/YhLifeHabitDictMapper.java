package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YhLifeHabitDict;

public interface YhLifeHabitDictMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhLifeHabitDict record);

    int insertSelective(YhLifeHabitDict record);

    YhLifeHabitDict selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhLifeHabitDict record);

    int updateByPrimaryKey(YhLifeHabitDict record);
    
    List<YhLifeHabitDict> selectbyhabit(Map<String, Object> map);
    
    int selectbyhabitCount(Map<String, Object> map);
    
    int selctbyhabitName(Map<String, Object> map);
    
    int CountHAbitType(int habitType);
    
    List<YhLifeHabitDict> showHabitName(Map<String, Object> map);
    
    List<YhLifeHabitDict> showhabitDictCodenull();
}