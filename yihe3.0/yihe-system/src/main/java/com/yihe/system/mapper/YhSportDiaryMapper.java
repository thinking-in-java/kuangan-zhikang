package com.yihe.system.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.InformationSummary;
import com.yihe.system.domain.YHUserSportDiaryVOJO;
import com.yihe.system.domain.YhFoodUserDiary;
import com.yihe.system.domain.YhSportDiary;

/**
 * 运动记录Mapper接口
 * 
 * @author yihe
 * @date 2020-04-08
 */
public interface YhSportDiaryMapper 
{
	
	List<InformationSummary> findSportDiaryByUserCodeForWeekDay(@Param("usercode")String usercode);
	List<InformationSummary> findSportUserDuaryLastWeek(@Param("usercode")String usercode);
	
	HashMap<String, Integer> findSportDiaryByUserCodeForMonth(@Param("usercode")String usercode,@Param("year")String year,@Param("month")String month);
	HashMap<String, Integer> findSportDiaryByuserCodeForyear(@Param("usercode")String usercode,@Param("year")String year);
	
	List<String> findSportDiaryByusercodeForDetails(@Param("usercode")String usercode);
	
	int findSportDiaryByUsercodeForCount(@Param("usercode")String usercode);
	
	List<YHUserSportDiaryVOJO> selectYhSportDiaryByusercodeForWeb(@Param("standard")String standard,@Param("usercode")String usercode,@Param("nameLike")String nameLike,@Param("usernameLike")String usernameLike,@Param("strTime")String strTime,@Param("endTime")String endTime);
    /**
     * 查询运动记录
     * 
     * @param uSportId 运动记录ID
     * @return 运动记录
     */
    public YhSportDiary selectYhSportDiaryById(Long uSportId);

    /**
     * 查询运动记录列表
     * 
     * @param yhSportDiary 运动记录
     * @return 运动记录集合
     */
    public List<YhSportDiary> selectYhSportDiaryList(YhSportDiary yhSportDiary);

    /**
     * 新增运动记录
     * 
     * @param yhSportDiary 运动记录
     * @return 结果
     */
    public int insertYhSportDiary(YhSportDiary yhSportDiary);

    /**
     * 修改运动记录
     * 
     * @param yhSportDiary 运动记录
     * @return 结果
     */
    public int updateYhSportDiary(YhSportDiary yhSportDiary);

    /**
     * 删除运动记录
     * 
     * @param uSportId 运动记录ID
     * @return 结果
     */
    public int deleteYhSportDiaryById(Long uSportId);

    /**
     * 批量删除运动记录
     * 
     * @param uSportIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteYhSportDiaryByIds(String[] uSportIds);
    
List<YhSportDiary> selectSportDiary(Map<String, Object> map);
	
	int selectSportDiaryConut(Map<String, Object> map);
	
	/**
	 *  查询所有食物记录模糊查询统计图
	 * @param map
	 * @return
	 */
	List<YhSportDiary> selectSportDiaryCtime(Map<String, Object> map);
	
	/**
	 * 查询运动总记录数
	 * @return
	 */
	int showSportSum();
	
	
	/**
	 * 查询运动每天总记录数
	 * @param entryTime
	 * @return
	 */
	int showSportTian();
	
	/**
	 * 根据时间查询记录分析报告
	 * @param map
	 * @return
	 */
	List<YhSportDiary> showSportRecordAnalyze(Map<String, Object> map);
	
	  int deleteByPrimaryKey(Integer uSportId);

	    int insert(YhSportDiary record);

	    int insertSelective(YhSportDiary record);


	    YhSportDiary selectByPrimaryKey(Integer uSportId);


	    int updateByPrimaryKeySelective(YhSportDiary record);

	    
	    int updateByPrimaryKeySelective2(YhSportDiary record);
	    
	    int updateByPrimaryKey(YhSportDiary record);
	    
	    YhSportDiary showsky(Map<String, Object> map);
	    
	    List<YhSportDiary> selectBySportTime(String userCode);
	    
	    List<YhSportDiary> selectBySportMonth(String userCode);
	    
	    List<YhSportDiary> selectBySportlastMonth(String userCode);
	    
	    List<YhSportDiary> selectBySportthisWeek(String userCode);
	    
	    List<YhSportDiary> selectBySportlastWeek(String userCode);
	    
	    List<YhSportDiary> selectBySportthisYear(String userCode);
	    
	    List<YhSportDiary> selectBySportlastYear(String userCode);
	    
	    YhSportDiary showSportWhetherThere(Map<String, Object> map);
	    
	    List<YhSportDiary> showSportStepStatistics(String userCode);
	    
	    /**
	     * 根据当前时间获取近七天的运动数据，去重usercode 
	     * @return
	     */
	    List<YhSportDiary> HebdomadRecordSport();
	    
	    YhSportDiary ShowpresentDiary(String userCode);
	    
	    int ShowpnumberDiary(String userCode);
	    
	    
	    List<YhSportDiary> showSportMonthUser(String userCode);
	    
	    List<YhSportDiary> showSportQuarter(String userCode);
	    
	    List<YhSportDiary> showSportYear(String userCode);
}
