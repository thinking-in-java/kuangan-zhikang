package com.yihe.applet.api.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.SleepRemindApp;
import com.yihe.applet.api.mapper.SleepRemindAppMapper;
import com.yihe.applet.api.service.SleepRemindAppService;
@Service
public class SleepRemindAppServiceImpl implements SleepRemindAppService{
	@Autowired
	SleepRemindAppMapper sleepRemindAppMapper;

	@Override
	public int insertSelective(SleepRemindApp record) {
		// TODO Auto-generated method stub
		return sleepRemindAppMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(SleepRemindApp record) {
		// TODO Auto-generated method stub
		return sleepRemindAppMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return sleepRemindAppMapper.deleteByPrimaryKey(id);
	}

	@Override
	public SleepRemindApp selectSleepRemindByParam(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sleepRemindAppMapper.selectSleepRemindByParam(map);
	}

	@Override
	public SleepRemindApp selectBySleepRemindApp(String userCode) {
		// TODO Auto-generated method stub
		return sleepRemindAppMapper.selectBySleepRemindApp(userCode);
	}
}
