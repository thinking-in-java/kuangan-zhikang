package com.yihe.system.service;

import com.yihe.system.domain.YhUserPersonageLive;

public interface YhUserPersonageLiveService{
	
	 YhUserPersonageLive selectByPrimaryKey(String userCode);
	 int updateByPrimaryKey(YhUserPersonageLive yhUserPersonageLive);
	 int insertSelective (YhUserPersonageLive yhUserPersonageLive);
}
