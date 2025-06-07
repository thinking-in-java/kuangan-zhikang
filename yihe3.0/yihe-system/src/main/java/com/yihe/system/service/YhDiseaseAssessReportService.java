package com.yihe.system.service;

import com.yihe.system.domain.*;
import org.springframework.ui.Model;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * YhDiseaseAssessReportService接口 Created by dyb on 2020/3/18.
 */
public interface YhDiseaseAssessReportService {
	
	
	String findDiseaseNameInfo(String code); 
	
	List<YHDiseaseAssessResolve> findAssessResolveInfoByrs(String rs,Integer did);
	
	//高血压发病相关的危险因素
	YhDiseaseRiskResultAppVOJO findDiseaseRiskResultByRsFor3(String rs);
	
	
	YhDiseaseRiskResultAppVOJO findAssessResultInfo(String rs);

	String findQuestionCheckForCheckAnswerByrs(String rs);

	YhDiseaseAssessReport findUserAssessReportInfoByTimeDesc(String usercode);
	List<YhDiseaseAssessReport> findDiseaseNameAndRiskLevelByRs(String rs, String sex) throws InvocationTargetException;

	List<YhReportDetailsVOJO> findDiseaseNameAndRiskLevelByRs(String rs, String sex, Model model,Object document,Object lastdocument) throws InvocationTargetException;

	List<YhDiseaseAssessReport> findAssessReportByRs(String rs);

	List<YhDiseaseAssessReport> findAssessReportInfoList(String usercode);

	long countByExample(YhDiseaseAssessReportExample example);

	int deleteByExample(YhDiseaseAssessReportExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(YhDiseaseAssessReport record);

	int selectByPrimaryKeyBycount(String usercode);

	int insertSelective(YhDiseaseAssessReport record);

	List<YhDiseaseAssessReport> selectByExample(YhDiseaseAssessReportExample example);

	List<YhDiseaseAssessReport> selectByPrimaryKeyByrequestserialNum(YhDicAssessmentScreen selectByPrimaryKey);

	YhDiseaseAssessReport selectByPrimaryKey(Integer id);

	int updateByExampleSelective(YhDiseaseAssessReport record, YhDiseaseAssessReportExample example);

	int updateByExample(YhDiseaseAssessReport record, YhDiseaseAssessReportExample example);

	int updateByPrimaryKeySelective(YhDiseaseAssessReport record);

	int updateByPrimaryKey(YhDiseaseAssessReport record);

	String findYhDiseaseAssessReportByAbnormal(String serialNum);

	List<Map<String,String>> findxls();
}