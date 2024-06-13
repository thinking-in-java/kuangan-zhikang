package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.HabitDiaryVojo;
import com.yihe.system.domain.YhLifeHabitSign;

public interface YhLifeHabitSignMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YhLifeHabitSign record);

    int insertSelective(YhLifeHabitSign record);

    YhLifeHabitSign selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhLifeHabitSign record);

    int updateByPrimaryKey(YhLifeHabitSign record);
    
    List<YhLifeHabitSign> selectSignDiary(Map<String, Object> map);
    
    List<YhLifeHabitSign> selectSignDiaryCount(Map<String, Object> map);
    
    List<YhLifeHabitSign> selectBySignDate(Map<String, Object> map);
    
    List<YhLifeHabitSign> selectHabitSignCtime(Map<String, Object> map);
    
    List<YhLifeHabitSign> selectBySign(Map<String, Object> map);
    
    List<YhLifeHabitSign> showSignCount(Map<String, Object> map);
    
    int ShowDaysStatistics(Map<String, Object> map);
    
    int ShowDaysStatisticspunch(Map<String, Object> map);
    
    List<YhLifeHabitSign> accumulativenum(Map<String, Object> map);
    
    List<YhLifeHabitSign> showpunch(Map<String, Object> map);
    
    List<YhLifeHabitSign> showmyhabit(Map<String, Object> map);
    
    List<YhLifeHabitSign> showCartogram(Map<String, Object> map);
    
    List<YhLifeHabitSign> HebdomadRecordHabit();
    
    List<HabitDiaryVojo> selectHabitDiaryUserAll(@Param("usercode")String usercode,@Param("nameLike")String nameLike,@Param("usernameLike")String usernameLike,@Param("strTime")String strTime,@Param("endTime")String endTime,@Param("standard")String standard);
    
    int deleteByPrimaryKeyCode(Map<String, Object> map);
}