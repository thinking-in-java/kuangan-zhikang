package com.yihe.applet.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhSysConfigApp;
import com.yihe.applet.api.mapper.YhSysConfigAppMapper;
import com.yihe.applet.api.service.YhSysConfigAppService;
@Service
public class YhSysConfigAppServiceImpl implements YhSysConfigAppService{
	@Autowired
	YhSysConfigAppMapper yhSysConfigAppMapper;
	@Override
	public YhSysConfigApp selectYhSysConfig() {
		// TODO Auto-generated method stub
		return yhSysConfigAppMapper.selectYhSysConfig();
	}

}
