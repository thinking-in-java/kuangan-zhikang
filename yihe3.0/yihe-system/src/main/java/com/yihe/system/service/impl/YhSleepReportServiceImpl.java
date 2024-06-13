/**
 * 
 */
package com.yihe.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yihe.system.domain.YhSleepReport;
import com.yihe.system.domain.YhSleepReportVojo;
import com.yihe.system.mapper.YhSleepReportMapper;
import com.yihe.system.service.YhSleepReportService;

/**
 * @author Administrator
 *
 */

@Service
@Transactional
public class YhSleepReportServiceImpl implements YhSleepReportService{
	@Autowired
	YhSleepReportMapper yhSleepReportMapper;

	@Override
	public List<YhSleepReport> selectSleep(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSleepReportMapper.selectSleep(map);
	}

	@Override
	public int selectSleepConut(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSleepReportMapper.selectSleepConut(map);
	}

	@Override
	public YhSleepReport showSleepDetail(Integer reportId) {
		// TODO Auto-generated method stub
		return yhSleepReportMapper.showSleepDetail(reportId);
	}

	@Override
	public int selectSleepReportByuserCodeForCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSleepReportMapper.selectSleepReportByuserCodeForCount(map);
	}

	@Override
	public String selectSleepReportByuserCodeForNewTime(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSleepReportMapper.selectSleepReportByuserCodeForNewTime(map);
	}

	@Override
	public List<YhSleepReportVojo> sleepAdministration(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSleepReportMapper.sleepAdministration(map);
	}

	@Override
	public List<YhSleepReportVojo> sleepAdministrationCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSleepReportMapper.sleepAdministrationCount(map);
	}

	@Override
	public int showSleepAssessUserCount(String userCode) {
		// TODO Auto-generated method stub
		return yhSleepReportMapper.showSleepAssessUserCount(userCode);
	}

	@Override
	public List<YhSleepReportVojo> assessRecordShow(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSleepReportMapper.assessRecordShow(map);
	}

	@Override
	public int assessRecordShowCount(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return yhSleepReportMapper.assessRecordShowCount(map);
	}
}
