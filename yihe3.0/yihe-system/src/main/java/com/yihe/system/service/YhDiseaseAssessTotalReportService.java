package com.yihe.system.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.HealthAssessmentVOJO;
import com.yihe.system.domain.YhDicAssessmentScreen;
import com.yihe.system.domain.YhDiseaseAssessReportVOJO;
import com.yihe.system.domain.YhDiseaseAssessTotalReport;
import com.yihe.system.domain.YhDiseaseAssessTotalReportExample;
import com.yihe.system.domain.YhUserMakeCard;

/**
 * YhDiseaseAssessTotalReportService接口 Created by dyb on 2020/3/20.
 */
public interface YhDiseaseAssessTotalReportService {
	
	 
	YhDiseaseAssessReportVOJO findAssessTotalByUsercodeNewInfo(String  usercode);
	
	PageInfo<YhDiseaseAssessReportVOJO> findAssessInfoByDocTor(YhDicAssessmentScreen selectByPrimaryKey,int pageNum,int pageSize);
	List<String> findAssessInfoByDocTor2(YhDicAssessmentScreen selectByPrimaryKey);

	int findAssessTotalCountSize( String  usercode);
	
	List<HealthAssessmentVOJO> findriskLevelByrequestSerialNum(String  requestSerialNum);
	
	PageInfo<HealthAssessmentVOJO> findAssessTotalByUserCode(String  usercode,String strTime,String endTime,int pageNum,int pageSize);
	
	int selectByPrimaryByUsercodeForCount( String  usercode);
	
	
	long countByExample(YhDiseaseAssessTotalReportExample example);

	int deleteByExample(YhDiseaseAssessTotalReportExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(YhDiseaseAssessTotalReport record);

	int insertSelective(YhDiseaseAssessTotalReport record);

	List<YhDiseaseAssessTotalReport> selectByExample(YhDiseaseAssessTotalReportExample example);

	YhDiseaseAssessTotalReport selectByPrimaryKey(Integer id);

	List<YhDiseaseAssessTotalReport> selectByPrimaryKeyByAge(YhDicAssessmentScreen selectByPrimaryKey);

	int updateByExampleSelective(YhDiseaseAssessTotalReport record, YhDiseaseAssessTotalReportExample example);

	int updateByExample(YhDiseaseAssessTotalReport record, YhDiseaseAssessTotalReportExample example);

	int updateByPrimaryKeySelective(YhDiseaseAssessTotalReport record);

	int updateByPrimaryKey(YhDiseaseAssessTotalReport record);

	PageInfo<YhDiseaseAssessReportVOJO> selectByPrimaryKeyByusercode(String usercode, String name, String username,
			String strtime, String endtime, int pageNum, int pageSize);

	List<YhDiseaseAssessReportVOJO> selectByPrimaryKeyByusercodeCount(String usercode, String name, String username,
			String strtime, String endtime);

	String selectByPrimaryKeyByusercodeForQtime(String usercode);

	String selectByPrimaryKeyByusercodeForCtime(String usercode);

	String selectByPrimaryKeyByusercodeForSporttime(String usercode);

	String selectByPrimaryKeyByusercodeForEnrolInfotime(String usercode);

	String selectByPrimaryKeyByusercodeForRequestSerialNum(String usercode);

}