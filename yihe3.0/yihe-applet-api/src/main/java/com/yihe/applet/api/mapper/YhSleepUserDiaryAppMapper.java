package com.yihe.applet.api.mapper;

import com.yihe.applet.api.domain.YhSleepUserDiaryApp;
import com.yihe.system.domain.YhFoodUserDiary;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version: shayu3.0
 * @Description: 睡眠记录mapper
 * @author: lhh
 * @date: 2020年4月15日 下午4:47:37
 */
public interface YhSleepUserDiaryAppMapper {
	Integer findSleepByuserCodeCount(String usercode);
	
	Integer findUserSleepLXNum(String usercode);
	Integer findUserSleepLXNum2(String usercode);
	YhSleepUserDiaryApp findTodaySleepInfo( String usercode);
	YhSleepUserDiaryApp findTodaySleepInfo2(@Param("usercode") String usercode,@Param("sleeptime") String sleeptime);
	/**
	 * 今年数据
	 * @param userCode
	 * @return
	 */
	List<YhSleepUserDiaryApp> selectthisyearData (String userCode);
	/**
	 * 去年
	 * @param userCode
	 * @return
	 */
	List<YhSleepUserDiaryApp> selectlastyearData(String userCode);
	/**
	 * 查询上周数据
	 * @param userCode
	 * @return
	 */
	List<YhSleepUserDiaryApp> selectlastweekData (String userCode);
	List<YhSleepUserDiaryApp> findSleepastWeekUserData (@Param("userCode") String userCode);
	/**
	 * 查询本周数据
	 * @param userCode
	 * @return
	 */
	List<YhSleepUserDiaryApp> selectthisweekData (String userCode);
	
	List<YhSleepUserDiaryApp> findSleepUserDuary (@Param("userCode") String userCode);
	HashMap<String, Integer> findSleepUserDuaryYue(@Param("userCode") String usercode, @Param("year") String year,
			@Param("month") String month);

	HashMap<String, Integer> findSleepUserDiaryByYear(@Param("userCode") String usercode, @Param("year") String year,@Param("month") String month);
	
	/**
	 * 查询上月数据
	 * @param userCode
	 * @return
	 */
	public List<YhSleepUserDiaryApp> selectlastmonthData(@Param("userCode") String userCode);
	
	
	List<YhSleepUserDiaryApp>  findSleepDiaryThisMonthInfo(@Param("userCode") String userCode);

	 
	List<YhSleepUserDiaryApp>  findSleepDiaryLastMonthInfo(@Param("userCode") String userCode);

		 
	List<YhSleepUserDiaryApp>  findSleepDiaryThisYearInfo(@Param("userCode") String userCode);

		 
	List<YhSleepUserDiaryApp>  findSleepDiaryLastYearInfo(@Param("userCode") String userCode);
	
	
	/*
	 * 添加用户睡眠记录
	 */
	public int addUserDiaryReport(YhSleepUserDiaryApp yhSleepUserDiary);
	/*
	 * 根据日期查询用户睡眠记录
	 */
	public YhSleepUserDiaryApp selUserDiaryByDate(YhSleepUserDiaryApp yhSleepUserDiary);
	/*
	 * 根据用户code查询所有记录
	 */
	public List<YhSleepUserDiaryApp> selAllDiaryByUser(@Param("userCode") String userCode);
	
	/*
	 * 查询连续天数
	 */
	public Map<Object, Object> selContinueDays(@Param("userCode") String userCode);

	/*
	 * 根据时间区间查询用户记录
	 */
	public List<YhSleepUserDiaryApp> selSleepDiaryByTimeAndCode(@Param("userCode") String userCode, @Param("startDiaryT") String startDiaryT, @Param("endDiaryT") String endDiaryT);
	
	/*
	 * 根据用户code修改睡眠日记录
	 */
	public int uptSleepDiaryByCode(YhSleepUserDiaryApp yhSleepUserDiary);
	
	
	/*
	 * 查询用户本月数据
	 */
	public List<YhSleepUserDiaryApp> selectThisMonthData(String usercode);
	/**
	 * 查询用户当天的睡眠记录
	 * @param userCode
	 * @return
	 */
	public YhSleepUserDiaryApp selectUserIntradayData(String userCode);
	
	public YhSleepUserDiaryApp selectsleepByParam(@Param("userCode") String userCode, @Param("sleepDiarytime") String sleepDiarytime);
}
