package com.yihe.applet.api.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yihe.applet.api.domain.IntegralShoppingGroupingApp;

public interface IntegralShoppingGroupingAppService {
	 List<IntegralShoppingGroupingApp> selectAllGrouping(@Param("orgCode") String orgCode);
}
