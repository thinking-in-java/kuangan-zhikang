package com.yihe.applet.api.mapper;

import java.util.List;

import com.yihe.applet.api.domain.YhUserDeviceApp;

public interface YhUserDeviceAppMapper {
	
	int findExistenceDevice(String tid);
	
	List<YhUserDeviceApp> findDeviceAllByUsercode(String usercode);
	
    int deleteByPrimaryKey(Integer tid);

    int insert(YhUserDeviceApp record);

    int insertSelective(YhUserDeviceApp record);

    YhUserDeviceApp selectByPrimaryKey(Integer tid);

    int updateByPrimaryKeySelective(YhUserDeviceApp record);

    int updateByPrimaryKey(YhUserDeviceApp record);
}