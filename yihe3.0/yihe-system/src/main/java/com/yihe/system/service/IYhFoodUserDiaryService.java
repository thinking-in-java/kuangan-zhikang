package com.yihe.system.service;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YhFoodCookBook;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.InformationSummary;
import com.yihe.system.domain.YHUserFoodDiaryVOJO;
import com.yihe.system.domain.YhFoodUserDiary;

/**
 * 用户膳食日志Service接口
 * 
 * @author yihe
 * @date 2020-04-07
 */
public interface IYhFoodUserDiaryService 
{

	String findFoodDiaryinfoNewCanci(String usercode);

	String findFoodDiaryinfoNewTime(String usercode);

	List<YhFoodUserDiary> findFoodInfoByTimeAnduserCodeForReport(String strTime,String endTime,String usercode);
	
	
	List<YhFoodUserDiary>  findFoodDiaryThisWeekInfo(String usercode);
	
	 
	List<YhFoodUserDiary> 	findFoodDiaryLastWeekInfo(String usercode);

		 
	List<YhFoodUserDiary>  findFoodDiaryThisMonthInfo(String usercode);

		 
	List<YhFoodUserDiary>  findFoodDiaryLastMonthInfo(String usercode);

		 
	List<YhFoodUserDiary>  findFoodDiaryThisYearInfo(String usercode);

		 
	List<YhFoodUserDiary>  findFoodDiaryLastYearInfo(String usercode);
	
	
	Integer findToExamine();
	/**
	 * 根据用户和时间修改
	 * @param yhFoodUserDiary
	 * @return
	 */
	public int updateYhFoodUserDiaryDel(YhFoodUserDiary yhFoodUserDiary);
	/**
	 * 根据用户和时间查询饮食日记
	 * @param usercode
	 * @param diaryData
	 * @return
	 */
	YhFoodUserDiary selectByUserCodeAndTime(String usercode, String diaryData);
	/**
	 * 日记时间集合
	 */
	List<String> findFoodUserDiaryForTimeList(String usercode);
	
	/**
	 * 小程序3.1查询用户本月膳食记录集合
	 */
	List<YhFoodUserDiary> findFoodUserDiaryByusercodeAndTime(String usercode,String time);
	
	/**
	 * 小程序3.1首页膳食 根据usercode查询当日膳食日记信息
	 * @param usercode
	 * @return
	 */
	YhFoodUserDiary findFoodUserDiaryInfoByUserCodeForNow( String usercode);
	
	
	int[] findFoodUserDuary(String usercode);
	int[] findFoodUserDuaryLastWeek(String usercode);
	int[] findFoodUserDuaryYue(String usercode,String year,String month);
	int[] findFoodUserDiaryByYear(String usercode,String year);
	
	int findFoodUserDiaRyBYusercodeForElements(String usercode);
	public PageInfo<YHUserFoodDiaryVOJO> findUserFoodDiaryInfo(String standard,String usercode,String nameLike,String usernameLike,String strTime,String endTime,int pageNum,int pageSize);
	   
	int findFlldUserDiaryByusercodeForCount(String usercode);
	
    /**
     * 查询用户膳食日志
     * 
     * @param uFdId 用户膳食日志ID
     * @return 用户膳食日志
     */
    public YhFoodUserDiary selectYhFoodUserDiaryById(Long uFdId);

    /**
     * 查询用户膳食日志列表
     * 
     * @param yhFoodUserDiary 用户膳食日志
     * @return 用户膳食日志集合
     */
    public List<YhFoodUserDiary> selectYhFoodUserDiaryList(YhFoodUserDiary yhFoodUserDiary);

    /**
     * 新增用户膳食日志
     * 
     * @param yhFoodUserDiary 用户膳食日志
     * @return 结果
     */
    public int insertYhFoodUserDiary(YhFoodUserDiary yhFoodUserDiary);

    /**
     * 修改用户膳食日志
     * 
     * @param yhFoodUserDiary 用户膳食日志
     * @return 结果
     */
    public int updateYhFoodUserDiary(YhFoodUserDiary yhFoodUserDiary);

    /**
     * 批量删除用户膳食日志
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteYhFoodUserDiaryByIds(String ids);

    /**
     * 删除用户膳食日志信息
     * 
     * @param uFdId 用户膳食日志ID
     * @return 结果
     */
    public int deleteYhFoodUserDiaryById(Long uFdId);
    
List<YhFoodUserDiary> selectDiary(Map<String, Object> map);
	
	int selectDiaryConut(Map<String, Object> map);
	
	List<YhFoodUserDiary> selectDiaryCtime(Map<String, Object> map);
	
	List<YhFoodUserDiary> showRecordAnalyze(Map<String, Object> map);
}
