package com.yihe.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhPainTotalReport;
import com.yihe.system.domain.YhPainTotalReportRecord;
import com.yihe.system.mapper.YhPainTotalReportMapper;
import com.yihe.system.service.YhPainTotalReportService;
@Service
public class YhPainTotalReportServiceImpl implements YhPainTotalReportService{
	@Autowired
	YhPainTotalReportMapper yhPainTotalReportMapper;
	@Override
	public PageInfo<YhPainTotalReport> queryUserByCriteriaWithPage(String userCode,String qtstrTime,String qtendTime, int page, int limit) {
		 //设置分页参数
        PageHelper.startPage(page,limit);
		// TODO Auto-generated method stub
		List<YhPainTotalReport> reportParam = yhPainTotalReportMapper.selectUserYhPainTotalReportParam(userCode, qtstrTime,qtendTime);
		return new PageInfo<YhPainTotalReport>(reportParam);
	}
	@Override
	public YhPainTotalReportRecord selectYhPainTotalReportParam(String userCode, Integer reportId) {
		// TODO Auto-generated method stub
		YhPainTotalReportRecord totalReportRecord = yhPainTotalReportMapper.selectYhPainTotalReportParam(userCode, reportId);
		return totalReportRecord;
	}
	@Override
	public YhPainTotalReport selectByPrimaryKey(Integer reportId) {
		// TODO Auto-generated method stub
		return yhPainTotalReportMapper.selectByPrimaryKey(reportId);
	}
	@Override
	public PageInfo<YhPainTotalReport> queryAllUserByCriteriaWithPage(String symptom,String orgCode,String name,String userCode, String qtstrTime,
			String qtendTime, int page, int limit) {
		 //设置分页参数
        PageHelper.startPage(page,limit);
        List<YhPainTotalReport> list = yhPainTotalReportMapper.selectUserAllYhPainTotalReportParam(symptom,orgCode,name,userCode, qtstrTime, qtendTime);
		return new PageInfo<YhPainTotalReport>(list);
	}

}
