package com.yihe.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YhPsychologicalAssessment;
import com.yihe.system.mapper.YhPsychologicalAssessmentMapper;
import com.yihe.system.service.YhPsychologicalAssessmentService;
@Service
public class YhPsychologicalAssessmentServiceImpl implements YhPsychologicalAssessmentService{
		@Autowired
		YhPsychologicalAssessmentMapper  YhPsychologicalAssessmentMapper;
	@Override
	public List<YhPsychologicalAssessment> selectAllmentalMentalHealth() {
		// TODO Auto-generated method stub
		return YhPsychologicalAssessmentMapper.selectAllmentalMentalHealth();
	}

}
