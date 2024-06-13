package com.yihe.system.service;

import com.yihe.system.domain.YhUserPersonageExisting;

public interface YhUserPersonageExistingService {
	 YhUserPersonageExisting selectByPrimaryKey(String userCode);
	 
	  int updateByPrimaryKey(YhUserPersonageExisting yhUserPersonageExisting);
	  
	  int insertSelective(YhUserPersonageExisting yhUserPersonageExisting);
}
