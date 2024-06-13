/**
 * 
 */
package com.yihe.system.service;

import com.yihe.system.domain.YhSportFrequencyWeb;

/**
 * @author dong
 *
 */
public interface YhSportFrequencyWebService {
	/**
	 * 根据userCode查询是否存在 
	 * @param userCode
	 * @return
	 */
	YhSportFrequencyWeb showUserFrequency(String userCode);
	
	 int updateByPrimaryKeySelective(YhSportFrequencyWeb record);
	 
	 int insertSelective(YhSportFrequencyWeb record);
}
