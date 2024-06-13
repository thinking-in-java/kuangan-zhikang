package com.yihe.system.mapper;

import com.yihe.system.domain.YhDiseaseAssessTotalReport2web;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface YhDiseaseAssessTotalReport2Mapper {
	YhDiseaseAssessTotalReport2web findAssessTotalReportLastInfo(@Param("usercode")String usercode,@Param("time")String time);
	
	YhDiseaseAssessTotalReport2web selectByPrimaryRs(String rs);
    int deleteByPrimaryKey(Integer id);

    int insert(YhDiseaseAssessTotalReport2web record);

    int insertSelective(YhDiseaseAssessTotalReport2web record);

    YhDiseaseAssessTotalReport2web selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YhDiseaseAssessTotalReport2web record);

    int updateByPrimaryKey(YhDiseaseAssessTotalReport2web record);
    
    int selectIllnessCoronary(Map<String, Object> map);
    
    List<YhDiseaseAssessTotalReport2web> HasassessedNum(Map<String, Object> map);
    
    List<YhDiseaseAssessTotalReport2web> Hasdisease(Map<String, Object> map);
    
    List<YhDiseaseAssessTotalReport2web> showHighRiskNum(Map<String, Object> map);
    
    List<YhDiseaseAssessTotalReport2web> ShowHealthNum(Map<String, Object> map);
    
    int selectByreport(String userCode);
    
    YhDiseaseAssessTotalReport2web showascReport(String userCode);
       
    YhDiseaseAssessTotalReport2web selectAssessByParam (String userCode);
}