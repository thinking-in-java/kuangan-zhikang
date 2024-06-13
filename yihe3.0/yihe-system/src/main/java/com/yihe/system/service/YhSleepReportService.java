/**
 * 
 */
package com.yihe.system.service;

import java.util.List;
import java.util.Map;

import com.yihe.system.domain.YhSleepReport;
import com.yihe.system.domain.YhSleepReportVojo;

/**
 * @author dong
 *
 */
public interface YhSleepReportService {
	

	/**
	 * 查询所以睡眠内容
	 * @param map
	 * @return
	 */
	List<YhSleepReport> selectSleep(Map<String, Object> map);
    
	/**
	 * 查询所以睡眠内容总行数
	 * @param map
	 * @return
	 */
    int selectSleepConut(Map<String, Object> map);
    int selectSleepReportByuserCodeForCount(Map<String, Object> map);
    
    String selectSleepReportByuserCodeForNewTime(Map<String, Object> map);
    
    /**
     * 根据id查询详情
     * @param map
     * @return
     */
    YhSleepReport showSleepDetail(Integer reportId);
    
    List<YhSleepReportVojo> sleepAdministration(Map<String, Object> map);
    
    List<YhSleepReportVojo> sleepAdministrationCount(Map<String, Object> map);
    
    int showSleepAssessUserCount(String userCode);
    
 List<YhSleepReportVojo> assessRecordShow(Map<String, Object> map);
    
    int assessRecordShowCount(Map<String, Object> map);
}
