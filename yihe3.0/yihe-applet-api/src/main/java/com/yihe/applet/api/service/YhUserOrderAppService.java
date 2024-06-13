package com.yihe.applet.api.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.YhUserOrderApp;

public interface YhUserOrderAppService {
	int insertSelective(YhUserOrderApp record);
    List<YhUserOrderApp> selectUserAllOrderbyParam( Integer page, Integer pageSize,
    		Integer month,@Param("type") Integer type,String userCode);
}
