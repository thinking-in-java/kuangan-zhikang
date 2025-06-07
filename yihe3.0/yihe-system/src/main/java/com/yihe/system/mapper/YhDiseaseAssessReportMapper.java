package com.yihe.system.mapper;

import com.yihe.system.domain.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface YhDiseaseAssessReportMapper {
	
	YHDiseaseRiskOptimal findYhDiseaseRiskOptimalByXpath(@Param("did") String did,@Param("isRadar") int isRadar,@Param("sex")String sex,@Param("xpath")String xpath);
	
	String findDiseaseNameInfo(@Param("code") String code);
	
	List<YHDiseaseAssessResolve> findAssessResolveInfoByrs(@Param("rs") String rs,@Param("did") Integer did);
	
	List<YHDiseaseAssessResolve> findAssessResolveInfoByrsInfo(@Param("rs") String rs);
	
	String findYhDiseaseRiskOptimalByStr(@Param("did") String did,@Param("isRadar") int isRadar,@Param("sex")String sex);
	
	List<YHDiseaseRiskOptimal> findYhDiseaseRiskOptimal(@Param("did") String did,@Param("isRadar") int isRadar,@Param("sex")String sex);
	
	List<YHDiseaseRiskOptimal> findYhDiseaseRiskOptimal2(@Param("did") String did,@Param("isRadar") int isRadar,@Param("sex")String sex);

	List<YHDiseaseRiskOptimal> findCSDiseaseRiskOptimal(@Param("did") String did, @Param("isRadar") int isRadar, @Param("sex")String sex);
	
	YhDiseaseRiskResultAppVOJO findDiseaseRiskResultByRsForRs(YhDiseaseRiskResultAppVOJO vojo);
	
	String findDiseaseRiskResultByRsForResultString(YhDiseaseRiskResultAppVOJO vojo);
	
	YhDiseaseRiskResultAppVOJO findDiseaseRiskResultByRsFor3(@Param("rs") String rs);
	
	YhDiseaseRiskResultAppVOJO findAssessResultInfo(@Param("rs") String rs);

	String findQuestionCheckForCheckAnswerByrs(@Param("rs") String rs);

	long countByExample(YhDiseaseAssessReportExample example);

	int deleteByExample(YhDiseaseAssessReportExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(YhDiseaseAssessReport record);

	int selectByPrimaryKeyBycount(@Param("usercode") String usercode);

	List<YhDiseaseAssessReport> findAssessReportInfoList(@Param("usercode") String usercode);

	List<YhDiseaseAssessReport> findAssessReportByRs(@Param("rs") String rs);

	List<YhDiseaseAssessReport> findDiseaseNameAndRiskLevelByRs(@Param("rs") String rs);

	List<YhReportDetailsVOJO> findDiseaseNameAndRiskLevelByRsNew(@Param("rs") String rs);

	int insertSelective(YhDiseaseAssessReport record);

	List<YhDiseaseAssessReport> selectByExample(YhDiseaseAssessReportExample example);

	YhDiseaseAssessReport findUserAssessReportInfoByTimeDesc(@Param("usercode") String usercode);

	List<YhDiseaseAssessReport> selectByPrimaryKeyByrequestserialNum(YhDicAssessmentScreen selectByPrimaryKey);

	YhDiseaseAssessReport selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") YhDiseaseAssessReport record,
			@Param("example") YhDiseaseAssessReportExample example);

	int updateByExample(@Param("record") YhDiseaseAssessReport record,
			@Param("example") YhDiseaseAssessReportExample example);

	int updateByPrimaryKeySelective(YhDiseaseAssessReport record);

	int updateByPrimaryKey(YhDiseaseAssessReport record);

	String findYhDiseaseAssessReportByAbnormal(String serialNum);

	List<Map<String,String>> findxls();

	int showDiseaseAssess();

	int showDiseaseAssessTian();

	List<YhDiseaseAssessReport> selectCurrentSituation();

	List<YhDiseaseAssessReport> selectHighRiskGroupCoronary(Map<String, Object> map);

	List<YhDiseaseAssessReport> selectHighRiskGroupStroke(Map<String, Object> map);

	List<YhDiseaseAssessReport> selectHighRiskGroupPressure(Map<String, Object> map);

	List<YhDiseaseAssessReport> selectHighRiskGroupDiabetes(Map<String, Object> map);

	List<YhDiseaseAssessReport> selectHighRiskGroupout(Map<String, Object> map);

	List<YhDiseaseAssessReport> selectHighRiskGroupDyslipidemia(Map<String, Object> map);

	List<YhDiseaseAssessReport> selectHighRiskGroupOsteoporosis(Map<String, Object> map);

	List<YhDiseaseAssessReport> selectHighRiskGroupObesity(Map<String, Object> map);

	List<YhDiseaseAssessReport> selectHighRiskGroupBreast(Map<String, Object> map);

	List<YhDiseaseAssessReport> selectHighRiskGroupLung(Map<String, Object> map);

	List<YhDiseaseAssessReport> selectHighRiskGroupPca(Map<String, Object> map);
}