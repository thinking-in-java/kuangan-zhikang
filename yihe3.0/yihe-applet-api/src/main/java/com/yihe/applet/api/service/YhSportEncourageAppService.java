/**
 * 
 */
package com.yihe.applet.api.service;

import java.util.List;
import java.util.Map;

import com.yihe.applet.api.domain.YhSportEncourageApp;
import com.yihe.applet.api.domain.YhUserSportChallengeApp;

/**
 * 挑战赛
 * @author dong
 *
 */
public interface YhSportEncourageAppService {
	/**
	 * 查询还在执行的数据
	 * @return
	 */
	  List<YhUserSportChallengeApp> selectByDnow(Map<String,Object> map);
	  
	  
	  int showEncourageNum(Map<String, Object> map);
	  /**
	   * 助威总数
	   * @param chId
	   * @return
	   */
	  List<YhSportEncourageApp> selectByChidSall(int chId);
	  
	  /**
	   * 增加助威
	   * @param record
	   * @return
	   */
	  int insertSelective(YhSportEncourageApp record);
}
