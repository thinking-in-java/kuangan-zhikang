package com.yihe.applet.api.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.IntegralShoppingGroupingApp;
import com.yihe.applet.api.mapper.IntegralShoppingGroupingAppMapper;
import com.yihe.applet.api.service.IntegralShoppingGroupingAppService;
@Service
public class IntegralShoppingGroupingAppServiceImpl implements IntegralShoppingGroupingAppService{
	@Autowired
	IntegralShoppingGroupingAppMapper integralShoppingGroupingAppMapper;
	@Override
	public List<IntegralShoppingGroupingApp> selectAllGrouping(String orgCode) {
		// TODO Auto-generated method stub
		return integralShoppingGroupingAppMapper.selectAllGrouping(orgCode);
	}

}
