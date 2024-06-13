package com.yihe.applet.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhUserMakeCardApp;
import com.yihe.applet.api.mapper.YhUserMakeCardAppMapper;
import com.yihe.applet.api.service.YhUserMakeCardAppService;

@Service
public class YhUserMakeCardAppServiceImpl implements YhUserMakeCardAppService{
	@Autowired
	YhUserMakeCardAppMapper yhUserMakeCardAppMapper;

	@Override
	public YhUserMakeCardApp selectOrgIdParam(String userCode) {
		// TODO Auto-generated method stub
		return yhUserMakeCardAppMapper.selectOrgIdParam(userCode);
	}
}
