package com.yihe.system.service;

import com.yihe.system.domain.YhUserRolePower;

/**
* YhUserRolePowerService接口
* Created by lll on 2020/3/6.
*/
public interface YhUserRolePowerService{
	 int inssert(YhUserRolePower record);

	 YhUserRolePower selectByPowerId(String userid);
	 
	  int updateByPrimaryKeySelective(YhUserRolePower record);
	
}