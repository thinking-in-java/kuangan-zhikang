package com.yihe.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhpressureAssessment;
import com.yihe.system.mapper.YhpressureAssessmentMapper;
import com.yihe.system.service.YhpressureAssessmentService;
@Service
public class YhpressureAssessmentServiceImpl implements YhpressureAssessmentService{
	@Autowired
	YhpressureAssessmentMapper 	yhpressureAssessmentMapper;

	@Override
	public List<YhpressureAssessment> selectAllPstriPressure() {
		// TODO Auto-generated method stub
		return yhpressureAssessmentMapper.selectAllPstriPressure();
	}

}
