package com.yihe.system.mapper;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YhSleepReport;
import com.yihe.system.domain.YhSleepReportVojo;

public interface YhSleepReportMapper {
	
	
    int deleteByPrimaryKey(Integer reportId);

    int insert(YhSleepReport record);

    int insertSelective(YhSleepReport record);

    YhSleepReport selectByPrimaryKey(Integer reportId);

    int updateByPrimaryKeySelective(YhSleepReport record);

    int updateByPrimaryKey(YhSleepReport record);
    
    List<YhSleepReport> selectSleep(Map<String, Object> map);
    
    int selectSleepConut(Map<String, Object> map);
    
    int selectSleepReportByuserCodeForCount(Map<String, Object> map);
    
    String selectSleepReportByuserCodeForNewTime(Map<String, Object> map);
    
    YhSleepReport showSleepDetail(Integer reportId);
    
    int showSleepAssess();
    
    int showSleepAssessTian();
    
    List<YhSleepReportVojo> sleepAdministration(Map<String, Object> map);
    
    List<YhSleepReportVojo> sleepAdministrationCount(Map<String, Object> map);
    
    int showSleepAssessUserCount(String userCode);
    
    List<YhSleepReportVojo> assessRecordShow(Map<String, Object> map);
    
    int assessRecordShowCount(Map<String, Object> map);
    
}