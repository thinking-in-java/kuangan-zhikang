package com.yihe.applet.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhPersonalityAssessmentApp;
import com.yihe.applet.api.mapper.YhPersonalityAssessmentAppMapper;
import com.yihe.applet.api.service.YhPersonalityAssessmentAppService;

@Service
public class YhPersonalityAssessmentAppServiceImpl implements YhPersonalityAssessmentAppService{
	@Autowired
	YhPersonalityAssessmentAppMapper yhPersonalityAssessmentAppMapper;

	@Override
	public int deleteByPrimaryKey(Integer paId) {
		// TODO Auto-generated method stub
		return yhPersonalityAssessmentAppMapper.deleteByPrimaryKey(paId);
	}

	@Override
	public int insertSelective(YhPersonalityAssessmentApp record) {
		// TODO Auto-generated method stub
		return yhPersonalityAssessmentAppMapper.insertSelective(record);
	}

	@Override
	public YhPersonalityAssessmentApp selectByPrimaryKey(Integer paId) {
		// TODO Auto-generated method stub
		return yhPersonalityAssessmentAppMapper.selectByPrimaryKey(paId);
	}

	@Override
	public int updateByPrimaryKeySelective(YhPersonalityAssessmentApp record) {
		// TODO Auto-generated method stub
		return yhPersonalityAssessmentAppMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(YhPersonalityAssessmentApp record) {
		// TODO Auto-generated method stub
		return yhPersonalityAssessmentAppMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<YhPersonalityAssessmentApp> selectAll() {
		// TODO Auto-generated method stub
		return yhPersonalityAssessmentAppMapper.selectAll();
	}
}
