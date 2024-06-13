package com.yihe.system.service;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserSportDiaryVOJO;
import com.yihe.system.domain.YhSportDiary;

import java.util.List;
import java.util.Map;

/**
 * 运动记录Service接口
 * 
 * @author yihe
 * @date 2020-04-08
 */
public interface IYhSportDiaryService 
{
	
	  int updateByPrimaryKeySelective2(YhSportDiary record);
	int[] findSportDiaryByUserCodeForWeekDay(String usercode);
	int[] findSportUserDuaryLastWeek(String usercode);
	int[] findSportDiaryByUserCodeForMonth(String usercode,String year,String month);
	int[] findSportDiaryByuserCodeForyear(String usercode,String year);
	int findSportDiaryByuserCodeForyear1(String usercode,String year);
	
	
	int findSportDiaryByusercodeForDetails(String usercode);
	
	
	int findSportDiaryByUsercodeForCount(String usercode);
	
	PageInfo<YHUserSportDiaryVOJO> selectYhSportDiaryByusercodeForWeb(String standard,String usercode,String nameLike,String usernameLike,String strTime,String endTime,int pageNum,int pageSize);
	   
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
     * 批量删除运动记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteYhSportDiaryByIds(String ids);

    /**
     * 删除运动记录信息
     * 
     * @param uSportId 运动记录ID
     * @return 结果
     */
    public int deleteYhSportDiaryById(Long uSportId);
    
    
List<YhSportDiary> selectSportDiary(Map<String, Object> map);
	
	int selectSportDiaryConut(Map<String, Object> map);
	
	/**
	 *  查询所有食物记录模糊查询统计图
	 * @param map
	 * @return
	 */
	List<YhSportDiary> selectSportDiaryCtime(Map<String, Object> map);
	
	/**
	 * 根据时间查询记录分析报告
	 * @param map
	 * @return
	 */
	List<YhSportDiary> showSportRecordAnalyze(Map<String, Object> map);
	
	/**
	 * 根据code和时间查询当前是否存在数据
	 * @param map
	 * @return
	 */
	  YhSportDiary showsky(Map<String, Object> map);
	  
	  /**
	   * 根据code查询用户运动日期
	   * @param userCode
	   * @return
	   */
	  List<YhSportDiary> selectBySportTime(String userCode);
	  
	  /**
	   * 根据code查询用户本月的记录
	   * @param userCode
	   * @return
	   */
	  List<YhSportDiary> selectBySportMonth(String userCode);
	  
	  /**
	   * 根据code查询用户上月的记录
	   * @param userCode
	   * @return
	   */
	  List<YhSportDiary> selectBySportlastMonth(String userCode);
	    
	  /**
	   * 根据code查询用户本周的记录
	   * @param userCode
	   * @return
	   */
	    List<YhSportDiary> selectBySportthisWeek(String userCode);
	    
	    /**
		   * 根据code查询用户上周的记录
	   * @param userCode
	   * @return
	   */
	    List<YhSportDiary> selectBySportlastWeek(String userCode);
	    
	    /**
		   * 根据code查询用户本年的记录
	   * @param userCode
	   * @return
	   */
	    List<YhSportDiary> selectBySportthisYear(String userCode);
	    /**
		   * 根据code查询用户上年的记录
	   * @param userCode
	   * @return
	   */
	    List<YhSportDiary> selectBySportlastYear(String userCode);
	    
	    /**
	     * 查询是否有步数
	     * @param map
	     * @return
	     */
	    YhSportDiary showSportWhetherThere(Map<String, Object> map);
	    
	    /**
	     * 查询步数统计
	     * @param userCode
	     * @return
	     */
	    List<YhSportDiary> showSportStepStatistics(String userCode);
	    
 int insertSelective(YhSportDiary record);
	    
	    int updateByPrimaryKeySelective(YhSportDiary record);
	    
	    int deleteByPrimaryKey(Integer uSportId);
	    
	    YhSportDiary selectByPrimaryKey(Integer uSportId);
	    
	    YhSportDiary ShowpresentDiary(String userCode);
	    
	    int ShowpnumberDiary(String userCode);
}
