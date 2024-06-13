package com.yihe.applet.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhWxInformationApp;
import com.yihe.applet.api.mapper.YhWxInformationAppMapper;
import com.yihe.applet.api.service.YhWxInformationAppService;
@Service
public class YhWxInformationAppServiceImpl implements YhWxInformationAppService{
	 @Autowired
	 private YhWxInformationAppMapper yhWxInformationAppMapper;

	@Override
	public List<YhWxInformationApp> selectAll() {
		// TODO Auto-generated method stub
		return yhWxInformationAppMapper.selectAll();
	}
	 
	 
}
