package com.yihe.applet.api.service.impl;

import com.yihe.applet.api.domain.YhPressureEvaluateApp;
import com.yihe.applet.api.mapper.YhPressureEvaluateAppMapper;
import com.yihe.applet.api.service.YhPressureEvaluateAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YhPressureEvaluateAppServiceImpl implements YhPressureEvaluateAppService{

	@Autowired
	YhPressureEvaluateAppMapper yhPressureEvaluateAppMapper;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhPressureEvaluateAppMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(YhPressureEvaluateApp record) {
		// TODO Auto-generated method stub
		return yhPressureEvaluateAppMapper.insert(record);
	}

	@Override
	public int insertSelective(YhPressureEvaluateApp record) {
		// TODO Auto-generated method stub
		return yhPressureEvaluateAppMapper.insertSelective(record);
	}

	@Override
	public YhPressureEvaluateApp selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhPressureEvaluateAppMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(YhPressureEvaluateApp record) {
		// TODO Auto-generated method stub
		return yhPressureEvaluateAppMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(YhPressureEvaluateApp record) {
		// TODO Auto-generated method stub
		return yhPressureEvaluateAppMapper.updateByPrimaryKey(record);
	}



	@Override
	public List<YhPressureEvaluateApp> selectAlList(Integer type, String userCode) {
		// TODO Auto-generated method stub
		return yhPressureEvaluateAppMapper.selectAlList(type, userCode);
	}

	@Override
	public List<YhPressureEvaluateApp> selectCount(String userCode) {
		// TODO Auto-generated method stub
		return yhPressureEvaluateAppMapper.selectCount(userCode);
	}

	@Override
	public int assessCount(Integer type) {
		return yhPressureEvaluateAppMapper.assessCount(type);
	}

	@Override
	public YhPressureEvaluateApp findUserRecord(Integer type, String userCode) {
		return yhPressureEvaluateAppMapper.findUserRecord(type,userCode);
	}

}
