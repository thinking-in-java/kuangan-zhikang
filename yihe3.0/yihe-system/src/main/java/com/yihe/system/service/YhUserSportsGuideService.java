/**
 * 
 */
package com.yihe.system.service;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YhUserSportsGuide;

/**
 * @author dong
 *
 */
public interface YhUserSportsGuideService {
	/**
	 * 查询用户查看运动分析报告
	 * @param map
	 * @return
	 */
	List<YhUserSportsGuide> selectAnalyzeSports(Map<String, Object> map);
    /**
     * 查询用户查看运动分析报告总行数
     * @param map
     * @return
     */
    int selectAnalyzeSportsConut(Map<String, Object> map);
    
    YhUserSportsGuide selectByPrimaryKey(Integer sportsGuideId);
    
    int updateByPrimaryKeySelective(YhUserSportsGuide record);
}
