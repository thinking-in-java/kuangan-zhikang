package com.yihe.system.service;


import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhPainTotalReport;
import com.yihe.system.domain.YhPainTotalReportRecord;

public interface YhPainTotalReportService {
	 public PageInfo<YhPainTotalReport> queryUserByCriteriaWithPage(String userCode,String qtstrTime,String qtendTime,int page,int limit);
	 public PageInfo<YhPainTotalReport> queryAllUserByCriteriaWithPage(String symptom,String orgCode,String name,String userCode,String qtstrTime,String qtendTime,int page,int limit);
	 YhPainTotalReportRecord selectYhPainTotalReportParam(String userCode, Integer reportId);
	 YhPainTotalReport selectByPrimaryKey(Integer reportId);

}
