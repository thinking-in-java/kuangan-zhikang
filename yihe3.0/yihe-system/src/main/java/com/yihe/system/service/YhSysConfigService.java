package com.yihe.system.service;

import com.yihe.system.domain.YhSysConfig;

/**
* YhSysConfigService接口
* Created by royiwen on 2020/3/25.
*/
public interface YhSysConfigService {
	
	/**
	 * 查询所有配置
	 * @return
	 */
	YhSysConfig showConfig();
}