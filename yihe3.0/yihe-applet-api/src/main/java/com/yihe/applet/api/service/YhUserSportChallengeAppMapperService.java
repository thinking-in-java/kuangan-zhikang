/**
 * 
 */
package com.yihe.applet.api.service;

import java.util.List;
import java.util.Map;

import com.yihe.applet.api.domain.YhUserSportChallengeApp;

/**
 * 个人挑战赛总记录表
 * @author dong
 *
 */
public interface YhUserSportChallengeAppMapperService {
	int insertSelective(YhUserSportChallengeApp record);
	/**
	 *  根据code查询是否有执行的数据
	 * @param userCode
	 * @return
	 */
	 Map<String, Object> selectByCodeDnow(String userCode);
	 
	 List<YhUserSportChallengeApp> selectByCodeXnow(Map<String,Object> map);
	 
	 YhUserSportChallengeApp selectByPrimaryKey(Integer chId);
	 
	 int selectByWhetherCount(int chId);
	 
	 int updateByPrimaryKeySelective(YhUserSportChallengeApp record);
	 
	 int showChWhetherNow(Map<String,Object> map);
}
