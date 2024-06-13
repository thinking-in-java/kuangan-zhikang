package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhSleepUserDiaryApp;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * @version: shayu3.0
 * @Description: 睡眠用户记录service
 * @author: lhh
 * @date: 2020年4月15日 下午5:06:45
 */
public interface YhSleepUserDiaryAppService {
	/**
	 * 查询用户本月连续数据
	 * @param usercode
	 * @return
	 */
	Integer findUserSleepLXNum2(String usercode);
	/**
	 * 查询上月数据
	 * @param userCode
	 * @return
	 */
	public List<YhSleepUserDiaryApp> selectlastmonthData(String userCode);
	
	List<YhSleepUserDiaryApp> selectlastweekData (String userCode);
	List<YhSleepUserDiaryApp> selectthisweekData (String userCode);
	List<YhSleepUserDiaryApp> selectthisyearData (String userCode);
	List<YhSleepUserDiaryApp> selectlastyearData(String userCode);
	//统计图
	int[] findSleepUserDuary (String userCode);
	int[] findSleepastWeekUserData (String userCode);
	int[] findSleepUserDuaryYue(String userCode,String year,String month);
	int[] findSleepUserDiaryByYear(String userCode,String year);
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
	public List<YhSleepUserDiaryApp> selAllDiaryByUser(String userCode);
	/*
	 * 查询连续天数
	 */
	public Map<Object, Object> selContinueDays(String userCode);
	/*
	 * 根据时间区间查询用户记录
	 */
	public List<YhSleepUserDiaryApp> selSleepDiaryByTimeAndCode(String userCode, String startDiaryT, String endDiaryT);
	/*
	 * 根据用户code修改睡眠日记录
	 */
	public int uptSleepDiaryByCode(YhSleepUserDiaryApp yhSleepUserDiary);
	
Integer findSleepByuserCodeCount(String usercode);
	
	Integer findUserSleepLXNum(String usercode);
	
	YhSleepUserDiaryApp findTodaySleepInfo2(String usercode,String sleeptime);
	YhSleepUserDiaryApp findTodaySleepInfo(String usercode);
	
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
	
	public YhSleepUserDiaryApp selectsleepByParam(String userCode,String sleepDiarytime);
}
