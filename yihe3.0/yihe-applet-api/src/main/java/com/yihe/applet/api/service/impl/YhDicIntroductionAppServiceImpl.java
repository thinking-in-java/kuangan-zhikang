package com.yihe.applet.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhDicIntroductionApp;
import com.yihe.applet.api.mapper.YhDicIntroductionAppMapper;
import com.yihe.applet.api.service.YhDicIntroductionAppService;
@Service
public class YhDicIntroductionAppServiceImpl implements YhDicIntroductionAppService{
	@Autowired
	YhDicIntroductionAppMapper yhDicIntroductionAppMapper;
	@Override
	public int insertSelective(YhDicIntroductionApp record) {
		// TODO Auto-generated method stub
		return yhDicIntroductionAppMapper.insertSelective(record);
	}

	@Override
	public YhDicIntroductionApp selectByPrimaryKey(Integer code) {
		// TODO Auto-generated method stub
		return yhDicIntroductionAppMapper.selectByPrimaryKey(code);
	}

	@Override
	public int updateByPrimaryKeySelective(YhDicIntroductionApp record) {
		// TODO Auto-generated method stub
		return yhDicIntroductionAppMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(YhDicIntroductionApp record) {
		// TODO Auto-generated method stub
		return yhDicIntroductionAppMapper.updateByPrimaryKey(record);
	}

	@Override
	public YhDicIntroductionApp selectBmi(String name) {
		// TODO Auto-generated method stub
		return yhDicIntroductionAppMapper.selectBmi(name);
	}

	@Override
	public YhDicIntroductionApp selectWHR(String name) {
		// TODO Auto-generated method stub
		return yhDicIntroductionAppMapper.selectWHR(name);
	}

	@Override
	public YhDicIntroductionApp selectExercise(String name) {
		// TODO Auto-generated method stub
		return yhDicIntroductionAppMapper.selectExercise(name);
	}

	@Override
	public YhDicIntroductionApp selecTnutrient(String name) {
		// TODO Auto-generated method stub
		return yhDicIntroductionAppMapper.selecTnutrient(name);
	}

	@Override
	public YhDicIntroductionApp selectEverydayConsumption(String name) {
		// TODO Auto-generated method stub
		return yhDicIntroductionAppMapper.selectEverydayConsumption(name);
	}

}
