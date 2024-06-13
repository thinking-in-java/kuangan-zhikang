package com.yihe.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhPersonalityAssessment;
import com.yihe.system.mapper.YhPersonalityAssessmentMapper;
import com.yihe.system.service.YhPersonalityAssessmentService;
@Service
public class YhPersonalityAssessmentServiceImpl implements YhPersonalityAssessmentService{
	@Autowired
	YhPersonalityAssessmentMapper yhPersonalityAssessmentMapper;
	@Override
	public List<YhPersonalityAssessment> selectAllTypeAPersonality() {
		// TODO Auto-generated method stub
		return yhPersonalityAssessmentMapper.selectAllTypeAPersonality();
	}

}
