package com.yihe.applet.api.mapper;

import java.util.List;

import com.yihe.applet.api.domain.YhdvdevicetypeApp;

public interface YhdvdevicetypeAppMapper {
	
	List<YhdvdevicetypeApp> findDeviceTypeListInfo();
	
    int deleteByPrimaryKey(String id);

    int insert(YhdvdevicetypeApp record);

    int insertSelective(YhdvdevicetypeApp record);

    YhdvdevicetypeApp selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(YhdvdevicetypeApp record);

    int updateByPrimaryKey(YhdvdevicetypeApp record);
}