package com.yihe.applet.api.service;



import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhJzSysSmsApp;



/**
* YhJzSysSmsService接口
* Created by dong on 2020/2/26.
*/
public interface YhJzSysSmsAppService  {
	YhJzSysSmsApp selectById(@Param("smsId") Integer smsId);
	List<YhJzSysSmsApp> selectYhJzSysSmsAppBytype();
    
}