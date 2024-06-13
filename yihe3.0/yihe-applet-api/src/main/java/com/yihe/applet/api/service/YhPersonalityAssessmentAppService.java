package com.yihe.applet.api.service;

import java.util.List;

import com.yihe.applet.api.domain.YhPersonalityAssessmentApp;

public interface YhPersonalityAssessmentAppService {
		int deleteByPrimaryKey(Integer paId);

	    int insertSelective(YhPersonalityAssessmentApp record);

	    YhPersonalityAssessmentApp selectByPrimaryKey(Integer paId);

	    int updateByPrimaryKeySelective(YhPersonalityAssessmentApp record);

	    int updateByPrimaryKey(YhPersonalityAssessmentApp record);
	    
	    List<YhPersonalityAssessmentApp> selectAll();

}
