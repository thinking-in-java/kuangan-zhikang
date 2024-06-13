package com.yihe.system.service;

import com.yihe.system.domain.YhUserPersonageViscera;

public interface YhUserPersonageVisceraService {

	YhUserPersonageViscera selectByPrimaryKey(String userCode);
	
	 int updateByPrimaryKey(YhUserPersonageViscera yhUserPersonageViscera);
	 int insertSelective(YhUserPersonageViscera yhUserPersonageViscera);
	
}
