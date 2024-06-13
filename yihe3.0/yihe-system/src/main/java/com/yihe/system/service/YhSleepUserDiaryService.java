/**
 * 
 */
package com.yihe.system.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YHUserSleepDiaryVOJO;
import com.yihe.system.domain.YhSleepUserDiary;

/**
 * @author dong
 *
 */
public interface YhSleepUserDiaryService {
	
	String findSleepUserDiaryByUserCodeForNew(String usercode);
	
	int[] findsleepDiaryByUserCodeForWeekDay(String usercode);
	int[] findSleepUserDuaryLastWeek(String usercode);
	int[] findsleepDiaryByUserCodeForMonth(String usercode,String year,String month);
	int[] findsleepDiaryByuserCodeForyear(String usercode,String year);

	/**
	 * 查询所有睡眠记录模糊查询
	 * @param map
	 * @return
	 */
	 List<YhSleepUserDiary> selectSleepDiary(Map<String, Object> map);
	    
	 /**
	  * 查询所有睡眠记录模糊查询总行数 
	  * @param map
	  * @return
	  */
	 int selectSleepDiaryConut(Map<String, Object> map);
	 
	 /**
	  * 查询所有睡眠记录模糊查询统计图
	  * @param map
	  * @return
	  */
	 List<YhSleepUserDiary> selectSleepDiaryCtime(Map<String, Object> map);
	 
	 /**
	  * 查询医生下用户的睡眠日记
	  * @param map
	  * @return
	  */
	 List<YHUserSleepDiaryVOJO> showSleepDiaryPor(Map<String, Object> map);
	 
	 /**
	  * 查询医生下用户的睡眠日记总行数
	  * @param map
	  * @return
	  */
	 int showSleepDiaryPorConut(Map<String, Object> map);
	 
	 /**
	  * 根据时间查询记录分析报告
	  * @param map
	  * @return
	  */
	 List<YhSleepUserDiary> showSleepRecordAnalyze(Map<String, Object> map);
	 
	  List<YhSleepUserDiary> showSleepMonthUser(String usercode);
	  
	  List<YhSleepUserDiary> showSleepQuarter(String usercode);
	    
	    List<YhSleepUserDiary> showSleepYear(String usercode);
}
