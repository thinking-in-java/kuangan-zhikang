package com.yihe.applet.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhpsychologicalAssessmentApp;
import com.yihe.applet.api.mapper.YhpsychologicalAssessmentAppMapper;
import com.yihe.applet.api.service.YhpsychologicalAssessmentAppService;

@Service
public class YhpsychologicalAssessmentAppServiceImpl implements YhpsychologicalAssessmentAppService{
	@Autowired
	YhpsychologicalAssessmentAppMapper yhpsychologicalAssessmentAppMapper;

	@Override
	public List<YhpsychologicalAssessmentApp> selectAllHeartHealth() {
		// TODO Auto-generated method stub
		return yhpsychologicalAssessmentAppMapper.selectAllHeartHealth();
	}
	
}
