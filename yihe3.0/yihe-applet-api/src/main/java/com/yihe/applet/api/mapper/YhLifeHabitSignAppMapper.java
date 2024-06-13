package com.yihe.applet.api.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhLifeHabitSignApp;
import com.yihe.system.domain.InformationSummary;
import com.yihe.system.domain.YhLifeHabitSign;

public interface YhLifeHabitSignAppMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhLifeHabitSignApp record);

    int insertSelective(YhLifeHabitSignApp record);

    YhLifeHabitSignApp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhLifeHabitSignApp record);

    int updateByPrimaryKey(YhLifeHabitSignApp record);
    
    int showHabitSignRecord(Map<String, Object> map);
    
    int selectBySignCount(int habitId);
    
    int ShowDaysStatistics(Map<String, Object> map);
    
    List<YhLifeHabitSignApp> showSignCount(Map<String, Object> map);
    
    List<YhLifeHabitSignApp> showThatverydayRanking(Integer habitId);
    
    List<YhLifeHabitSignApp> selectByLifeTime(String userCode);
    
    List<YhLifeHabitSignApp> ShowthisweekSign(Map<String, Object> map);
    
    List<YhLifeHabitSignApp> ShowthismonthSign(Map<String, Object> map);
    
    List<YhLifeHabitSignApp> ShowthisAllSign(Map<String, Object> map);
    
    int ShowMaxSign(Map<String, Object> map);
    
    
    List<InformationSummary> findHabitDiaryByUserCodeForWeekDay(@Param("usercode")String usercode,@Param("habitId")int habitId);
    
    HashMap<String, Integer> findHabitDiaryByUserCodeForMonth(@Param("usercode")String usercode,@Param("year")String year,@Param("month")String month,@Param("habitId")int habitId);
	
    HashMap<String, Integer> findHabitDiaryByuserCodeForyear(@Param("usercode")String usercode,@Param("year")String year,@Param("habitId")int habitId);
	
    
    int showSignhabitidCount(int habitId);
}