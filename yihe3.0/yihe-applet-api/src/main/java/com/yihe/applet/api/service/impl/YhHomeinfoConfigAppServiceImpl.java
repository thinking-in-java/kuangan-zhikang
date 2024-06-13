package com.yihe.applet.api.service.impl;

import com.yihe.applet.api.domain.YhHomeinfoConfigApp;
import com.yihe.applet.api.mapper.YhHomeinfoConfigAppMapper;
import com.yihe.applet.api.service.YhHomeinfoConfigAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YhHomeinfoConfigAppServiceImpl implements YhHomeinfoConfigAppService{
	@Autowired
	private YhHomeinfoConfigAppMapper yhHomeinfoConfigMapper;
	
	@Override
	public List<YhHomeinfoConfigApp> selAllData() {
		// TODO Auto-generated method stub
		return yhHomeinfoConfigMapper.selAllData();
	}
	
}
