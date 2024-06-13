package com.yihe.system.service;

import com.yihe.system.domain.YhUserPersonagePhysical;

public interface YhUserPersonagePhysicalService {
	   YhUserPersonagePhysical selectByPrimaryKey(String userCode);
	   int updateByPrimaryKey(YhUserPersonagePhysical yhUserPersonagePhysical);
	   int insertSelective(YhUserPersonagePhysical yhUserPersonagePhysical);
}
