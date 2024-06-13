package com.yihe.applet.api.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhScoreEventCategoryApp;
import com.yihe.applet.api.mapper.YhScoreEventCategoryAppMapper;
import com.yihe.applet.api.service.YhScoreEventCategoryAppService;
@Service
public class YhScoreEventCategoryAppServiceImpl implements YhScoreEventCategoryAppService{
	@Autowired
	YhScoreEventCategoryAppMapper yhScoreEventCategoryAppMapper;
	@Override
	public YhScoreEventCategoryApp selectEventType(String eventType) {
		// TODO Auto-generated method stub
		return yhScoreEventCategoryAppMapper.selectEventType(eventType);
	}

	@Override
	public List<Map<String, Object>> selectIntegralStatus(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhScoreEventCategoryAppMapper.selectIntegralStatus(map);
	}

	@Override
	public String seleventType(String eventName) {
		return yhScoreEventCategoryAppMapper.seleventType(eventName);
	}

}
