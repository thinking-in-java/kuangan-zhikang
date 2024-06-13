package com.yihe.system.service;

import com.yihe.system.domain.YhUserPersonageSymptom;

public interface YhUserPersonageSymptomService {
	
	YhUserPersonageSymptom selectByPrimaryKey(String userCode);
	
	 int updateByPrimaryKey(YhUserPersonageSymptom yhUserPersonageSymptom);
	 
	 int insertSelective(YhUserPersonageSymptom yhUserPersonageSymptom);
	  int updateByPrimaryKeySelective(YhUserPersonageSymptom yhUserPersonageSymptom);
}
