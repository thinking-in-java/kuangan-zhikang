package com.yihe.applet.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhPressureAssessmentApp;
import com.yihe.applet.api.mapper.YhPressureAssessmentAppMapper;
import com.yihe.applet.api.service.YhPressureAssessmentAppService;
@Service
public class YhPressureAssessmentAppServiceImpl implements YhPressureAssessmentAppService{
	@Autowired
	YhPressureAssessmentAppMapper yhPressureAssessmentAppMapper;
	
	@Override
	public List<YhPressureAssessmentApp> selectAllpstre() {
		// TODO Auto-generated method stub
		return yhPressureAssessmentAppMapper.selectAllpstre();
	}

}
