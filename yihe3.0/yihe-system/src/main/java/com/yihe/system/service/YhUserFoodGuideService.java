/**
 * 
 */
package com.yihe.system.service;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YhUserFoodGuide;

/**
 * @author dong
 *
 */
public interface YhUserFoodGuideService {
	
	YhUserFoodGuide findUserFoodGuiDeByid(Integer id);

	/**
	 * 查询用户查看食物分析报告
	 * @param map
	 * @return
	 */
	List<YhUserFoodGuide> selectAnalyze(Map<String, Object> map);
    
	/**
	 * 查询用户查看食物分析报告总行数
	 * @param map
	 * @return
	 */
    int selectAnalyzeConut(Map<String, Object> map);
    
    YhUserFoodGuide selectByPrimaryKey(Integer foodguideId);
    
}
