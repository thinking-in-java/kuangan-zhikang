package com.yihe.system.mapper;

import com.yihe.system.domain.HealthAssessmentVOJO;
import com.yihe.system.domain.YhDicAssessmentScreen;
import com.yihe.system.domain.YhDiseaseAssessReportVOJO;
import com.yihe.system.domain.YhDiseaseAssessTotalReport;
import com.yihe.system.domain.YhDiseaseAssessTotalReportExample;
import com.yihe.system.domain.YhDiseaseRiskResultAppVOJO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface YhDiseaseAssessTotalReportMapper {

	 

	YhDiseaseAssessReportVOJO findAssessTotalByUsercodeNewInfo(@Param("usercode") String  usercode);
	
	String	findCategoryByCode(@Param("code")String code);
	
	List<YhDiseaseAssessReportVOJO> findAssessInfoByDocTor(YhDicAssessmentScreen vojo);

	List<String> findAssessInfoByDocTor2(YhDicAssessmentScreen vojo);

	int findAssessTotalCountSize(@Param("usercode") String  usercode);
	
	List<HealthAssessmentVOJO> findriskLevelByrequestSerialNum(@Param("requestSerialNum") String  requestSerialNum);
	
	List<HealthAssessmentVOJO> findAssessTotalByUserCode(@Param("usercode") String  usercode,@Param("strTime") String  strTime,@Param("endTime") String  endTime);
	
	long countByExample(YhDiseaseAssessTotalReportExample example);

	int deleteByExample(YhDiseaseAssessTotalReportExample example);

	int deleteByPrimaryKey(Integer id);
	
	int selectByPrimaryByUsercodeForCount(@Param("usercode") String  usercode);

	int insert(YhDiseaseAssessTotalReport record);

	int insertSelective(YhDiseaseAssessTotalReport record);

	List<YhDiseaseAssessTotalReport> selectByExample(YhDiseaseAssessTotalReportExample example);

	List<YhDiseaseAssessReportVOJO> selectByPrimaryKeyByusercode(@Param("usercode") String usercode,
			@Param("name") String name, @Param("username") String username, @Param("strtime") String strtime,
			@Param("endtime") String endtime);

	List<YhDiseaseAssessReportVOJO> findPGByUserCode(@Param("userCode") String userCode);

	List<YhDiseaseAssessReportVOJO> selectByPrimaryKeyByusercodeCount(@Param("usercode") String usercode,
			@Param("name") String name, @Param("username") String username, @Param("strtime") String strtime,
			@Param("endtime") String endtime);

	YhDiseaseAssessTotalReport selectByPrimaryKey(Integer id);

	List<YhDiseaseAssessTotalReport> selectByPrimaryKeyByAge(YhDicAssessmentScreen selectByPrimaryKey);

	int updateByExampleSelective(@Param("record") YhDiseaseAssessTotalReport record,
			@Param("example") YhDiseaseAssessTotalReportExample example);

	int updateByExample(@Param("record") YhDiseaseAssessTotalReport record,
			@Param("example") YhDiseaseAssessTotalReportExample example);

	int updateByPrimaryKeySelective(YhDiseaseAssessTotalReport record);

	int updateByPrimaryKey(YhDiseaseAssessTotalReport record);

	String selectByPrimaryKeyByusercodeForQtime(String usercode);

	String selectByPrimaryKeyByusercodeForCtime(String usercode);
	String selectByPrimaryKeyByusercodeForRequestSerialNum(String usercode);

	String selectByPrimaryKeyByusercodeForSporttime(String usercode);

	String selectByPrimaryKeyByusercodeForEnrolInfotime(String usercode);
	
	List<YhDiseaseAssessTotalReport> indexshowHigh(Map<String, Object> map);
}