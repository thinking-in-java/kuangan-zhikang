package com.yihe.applet.api.service;

import java.util.List;

import com.yihe.applet.api.domain.YhUserDeviceApp;

public interface YhUserDeviceAppService {
	
	int findExistenceDevice(String tid);
	
	
	List<YhUserDeviceApp> findDeviceAllByUsercode(String usercode);

	int deleteByPrimaryKey(Integer tid);

	int insertSelective(YhUserDeviceApp record);

	YhUserDeviceApp selectByPrimaryKey(Integer tid);

	int updateByPrimaryKeySelective(YhUserDeviceApp record);

}
