package com.yihe.applet.api.service;

import java.util.Map;

import com.yihe.applet.api.domain.YhSportCollect;

/**
* YhSportCollectService接口
* Created by dong on 2020/2/25.
*/
public interface YhSportCollectService{
	
	 int insertSelective(YhSportCollect record);
	 
	  int showCollectRepetition(Map<String, Object> map);
	  
	  int deleteByPrimaryKeymap(Map<String, Object> map);
}