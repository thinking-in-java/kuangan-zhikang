/**
 * 
 */
package com.yihe.system.service;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YhSportMotion;

/**
 * @author dong
 *
 */
public interface YhSportMotionService {

	/**
	 * 查询所有运动模糊查询
	 * @param map
	 * @return
	 */
	List<YhSportMotion> selectSport(Map<String, Object> map);
    
	/**
	 * 查询所有运动模糊查询总行数
	 * @param map
	 * @return
	 */
    int selectSportConut(Map<String, Object> map);
    
    
    int insertSelective(YhSportMotion record);
    int updateByPrimaryKeySelective(YhSportMotion record);
    
    int deleteByPrimaryKey(Integer id);
    YhSportMotion selectByPrimaryKey(Integer id);
    

    int selctSportNmaeRepetition(Map<String, Object> map);
    
    /**
	 * 模糊查询
	 * @param map
	 * @return
	 */
	List<YhSportMotion> selectByname(Map<String, Object> map);
	
	/**
	 * 查询热门运动
	 * @param map
	 * @return
	 */
	List<YhSportMotion> selectByhot(Map<String, Object> map);
	
	/**
	 * 查询用户收藏运动
	 * @param userCode
	 * @return
	 */
	List<YhSportMotion> selectByhotCollect(String userCode);
	
	/**
	 * 查询运动热量
	 * @param sportName
	 * @return
	 */
	YhSportMotion sportShowName(String sportName);
	/**
	 * 查询最新的10条运动名称
	 * @return
	 */
	List<YhSportMotion> selectNewestTendata();
	/**
	 * 模糊查询五条运动名称
	 * @param sportName
	 * @return
	 */
	List<YhSportMotion> selectSportNameByParam(String sportName);
	
}
