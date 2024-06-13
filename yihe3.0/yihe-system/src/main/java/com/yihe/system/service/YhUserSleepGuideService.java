/**
 * 
 */
package com.yihe.system.service;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YhUserSleepGuide;

/**
 * @author dong
 *
 */
public interface YhUserSleepGuideService {

	/**
	 * 查询用户查看睡眠分析报告
	 * @param map
	 * @return
	 */
	List<YhUserSleepGuide> selectAnalyzeSleep(Map<String, Object> map);
	/**
	 * 查询用户查看睡眠分析报告总行数
	 * @param map
	 * @return
	 */
    int selectAnalyzeSleepConut(Map<String, Object> map);
    
    YhUserSleepGuide selectByPrimaryKey(Integer sleepGuideId);
}
