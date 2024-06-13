package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhLifeHabitPersonDictApp;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface YhLifeHabitPersonDictAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhLifeHabitPersonDictApp record);

    int insertSelective(YhLifeHabitPersonDictApp record);

    YhLifeHabitPersonDictApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhLifeHabitPersonDictApp record);

    int updateByPrimaryKey(YhLifeHabitPersonDictApp record);
    
    List<YhLifeHabitPersonDictApp> showUserCodePersonDict(String userCode);
    
    int showhabit(Map<String, Object> map);
    
    List<YhLifeHabitPersonDictApp> showByHabitRecord(Map<String, Object> map);
    
    int selectByPersonCount(int habitId);
    
    List<YhLifeHabitPersonDictApp> showZongRanking(int habitId);
    
    YhLifeHabitPersonDictApp ShowOneselfRanking(Map<String, Object> map);
    
    int showHabitidcun(Map<String, Object> map);
    
    YhLifeHabitPersonDictApp showUserHabit(Map<String, Object> map);
    
    YhLifeHabitPersonDictApp selectByPersonId(int id);
    
    YhLifeHabitPersonDictApp showUserHabit1(Map<String, Object> map);

    Date setDate(@Param("habitId")Integer habitId,@Param("userCode")String userCode);

    List<YhLifeHabitPersonDictApp> selectByHabtisListCondition();

    int updateByHabyisListCondition(YhLifeHabitPersonDictApp yhLifeHabitPersonDictApp);
}