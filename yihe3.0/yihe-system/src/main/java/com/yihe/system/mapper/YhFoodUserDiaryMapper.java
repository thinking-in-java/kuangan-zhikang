package com.yihe.system.mapper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.InformationSummary;
import com.yihe.system.domain.YHUserFoodDiaryVOJO;
import com.yihe.system.domain.YhFoodUserDiary;

/**
 * 用户膳食日志Mapper接口
 * 
 * @author yihe
 * @date 2020-04-07
 */
public interface YhFoodUserDiaryMapper {



	String findFoodDiaryinfoNewCanci(String usercode);
	String findFoodDiaryinfoNewTime(String usercode);
	List<YhFoodUserDiary> findFoodDiaryAll();
	String findFoodDiaryLimit1Time();
	List<String> findFoodDiaryListUser();
	List<YhFoodUserDiary> findFoodDiaryList(@Param("strtime")String strtime,@Param("endtime")String endtime,@Param("usercode")String usercode);
	
	List<YhFoodUserDiary> findWeekInfoByTimeAndUsercode(@Param("usercode")String usercode,@Param("statime")Date statime,@Param("endtime")Date endtime);
	
	int updateYhFoodUserDiary2(YhFoodUserDiary yhFoodUserDiary);
	
	List<YhFoodUserDiary> foodtransfer();
	
	List<String> findWeekInfoForUserCode();
	
	List<String> showfoodformatMonth();
	
	List<String> showfoodQujilu();
	
	List<String> showfoodNianjilu();
	
	List<YhFoodUserDiary> findWeekInfo(String usercode);
	
	List<YhFoodUserDiary> showfoodMonthUser(String usercode);
	
	List<YhFoodUserDiary> findFoodInfoByTimeAnduserCodeForReport(String strTime,String endTime,String usercode);
	
	
	
	List<YhFoodUserDiary>  findFoodDiaryThisWeekInfo(String usercode);
		
		 
	List<YhFoodUserDiary> 	findFoodDiaryLastWeekInfo(String usercode);

		 
	List<YhFoodUserDiary>  findFoodDiaryThisMonthInfo(String usercode);

		 
	List<YhFoodUserDiary>  findFoodDiaryLastMonthInfo(String usercode);

		 
	List<YhFoodUserDiary>  findFoodDiaryThisYearInfo(String usercode);

		 
	List<YhFoodUserDiary>  findFoodDiaryLastYearInfo(String usercode);

	
	
	
	Integer findToExamine();
	/**
	 * 根据用户id和时间修改饮食日记
	 */
	public int updateYhFoodUserDiaryDel(YhFoodUserDiary yhFoodUserDiary);
	
	/**
	 * 根据用户和时间查询膳食日记
	 * @param usercode
	 * @param diaryData
	 * @return
	 */
	YhFoodUserDiary selectByUserCodeAndTime(@Param("usercode") String usercode, @Param("diaryData") String diaryData);
	
	/**
	 * 日记时间集合
	 */
	List<String> findFoodUserDiaryForTimeList(@Param("usercode")String usercode);
	
	/**
	 * 小程序3.1查询用户本月膳食记录集合
	 */
	List<YhFoodUserDiary> findFoodUserDiaryByusercodeAndTime(@Param("usercode")String usercode,@Param("time")String time);
	
	
	/**
	 * 小程序3.1首页膳食 根据usercode查询当日膳食日记信息
	 * @param usercode
	 * @return
	 */
	YhFoodUserDiary findFoodUserDiaryInfoByUserCodeForNow(@Param("usercode") String usercode);

	List<InformationSummary> findFoodUserDuary(@Param("usercode") String usercode);

	List<InformationSummary> findFoodUserDuaryLastWeek(@Param("usercode") String usercode);

	List<String> findFoodUserDiaRyBYusercodeForElements(@Param("usercode") String usercode);

	HashMap<String, Integer> findFoodUserDuaryYue(@Param("usercode") String usercode, @Param("year") String year,
			@Param("month") String month);

	HashMap<String, Integer> findFoodUserDiaryByYear(@Param("usercode") String usercode, @Param("year") String year);
	HashMap<String, Integer> findFoodUserDiaryByYear2(@Param("usercode") String usercode, @Param("year") String year,@Param("month") String month);

	int findFlldUserDiaryByusercodeForCount(@Param("usercode") String usercode);

	/**
	 * 查询日记信息 某医生所管理的
	 */
	public List<YHUserFoodDiaryVOJO> findUserFoodDiaryInfo(@Param("standard")String standard,@Param("usercode") String usercode,
			@Param("nameLike") String nameLike, @Param("usernameLike") String usernameLike,
			@Param("strTime") String strTime, @Param("endTime") String endTime);

	/**
	 * 查询用户膳食日志
	 * 
	 * @param uFdId
	 *            用户膳食日志ID
	 * @return 用户膳食日志
	 */
	public YhFoodUserDiary selectYhFoodUserDiaryById(Long uFdId);

	/**
	 * 查询用户膳食日志列表
	 * 
	 * @param yhFoodUserDiary
	 *            用户膳食日志
	 * @return 用户膳食日志集合
	 */
	public List<YhFoodUserDiary> selectYhFoodUserDiaryList(YhFoodUserDiary yhFoodUserDiary);

	/**
	 * 新增用户膳食日志
	 * 
	 * @param yhFoodUserDiary
	 *            用户膳食日志
	 * @return 结果
	 */
	public int insertYhFoodUserDiary(YhFoodUserDiary yhFoodUserDiary);

	/**
	 * 修改用户膳食日志
	 * 
	 * @param yhFoodUserDiary
	 *            用户膳食日志
	 * @return 结果
	 */
	public int updateYhFoodUserDiary(YhFoodUserDiary yhFoodUserDiary);

	/**
	 * 删除用户膳食日志
	 * 
	 * @param uFdId
	 *            用户膳食日志ID
	 * @return 结果
	 */
	public int deleteYhFoodUserDiaryById(Long uFdId);

	/**
	 * 批量删除用户膳食日志
	 * 
	 * @param uFdIds
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int deleteYhFoodUserDiaryByIds(String[] uFdIds);
	
	
	List<YhFoodUserDiary> selectDiary(Map<String, Object> map);
	
	int selectDiaryConut(Map<String, Object> map);
	
	/**
	 *  查询所有食物记录模糊查询统计图
	 * @param map
	 * @return
	 */
	List<YhFoodUserDiary> selectDiaryCtime(Map<String, Object> map);
	
	/**
	 * 查询膳食总记录数
	 * @return
	 */
	int showFoodSum();
	
	/**
	 * 查询膳食每天总记录数
	 * @param cTime
	 * @return
	 */
	int showFoodTian();
	
	List<YhFoodUserDiary> showRecordAnalyze(Map<String, Object> map);
	
	List<YhFoodUserDiary> HebdomadRecordFood();
	
	List<YhFoodUserDiary> showfoodQuarter(String usercode);
	
	List<YhFoodUserDiary> showfoodYear(String usercode);
}
