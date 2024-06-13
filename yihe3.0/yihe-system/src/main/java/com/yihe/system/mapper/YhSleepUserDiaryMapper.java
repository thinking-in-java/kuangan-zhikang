package com.yihe.system.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.InformationSummary;
import com.yihe.system.domain.YHUserSleepDiaryVOJO;
import com.yihe.system.domain.YhSleepUserDiary;

public interface YhSleepUserDiaryMapper {
	
	String findSleepUserDiaryByUserCodeForNew(@Param("usercode")String usercode);
	
List<InformationSummary> findsleepDiaryByUserCodeForWeekDay(@Param("usercode")String usercode);
List<InformationSummary> findSleepUserDuaryLastWeek(@Param("usercode")String usercode);

	HashMap<String, Integer> findsleepDiaryByUserCodeForMonth(@Param("usercode")String usercode,@Param("year")String year,@Param("month")String month);
	HashMap<String, Integer> findsleepDiaryByuserCodeForyear(@Param("usercode")String usercode,@Param("year")String year);
	
	
	
    int deleteByPrimaryKey(Integer id);

    int insert(YhSleepUserDiary record);

    int insertSelective(YhSleepUserDiary record);

    YhSleepUserDiary selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhSleepUserDiary record);

    int updateByPrimaryKey(YhSleepUserDiary record);
    
    List<YhSleepUserDiary> selectSleepDiary(Map<String, Object> map);
    
    int selectSleepDiaryConut(Map<String, Object> map);
    
    List<YhSleepUserDiary> selectSleepDiaryCtime(Map<String, Object> map);
    
    List<YHUserSleepDiaryVOJO> showSleepDiaryPor(Map<String, Object> map);
    
    int showSleepDiaryPorConut(Map<String, Object> map);
    
    int showSleepSum();
    
    int showSleepTian();
    
    List<YhSleepUserDiary> showSleepRecordAnalyze(Map<String, Object> map);
    
    List<YhSleepUserDiary> HebdomadRecordSleep();
    
    List<String> showsleepformatMonth();
    
    List<String> showsleepQujilu();
    
    List<String> showsleepNianjilu();
    
     
    List<YhSleepUserDiary> showSleepMonthUser(String usercode);
    
    List<YhSleepUserDiary> showSleepQuarter(String usercode);
    
    List<YhSleepUserDiary> showSleepYear(String usercode);
}