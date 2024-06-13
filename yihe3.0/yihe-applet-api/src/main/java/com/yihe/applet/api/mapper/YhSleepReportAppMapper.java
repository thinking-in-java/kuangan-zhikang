package com.yihe.applet.api.mapper;


import com.yihe.applet.api.domain.YhSleepReportApp;

import java.util.List;

/**
 * @version: shayu3.0
 * @Description: 睡眠评估结果mapper
 * @author: lhh
 * @date: 2020年4月15日 下午4:46:00
 */
public interface YhSleepReportAppMapper {
	/*
	 * 添加评估记录
	 */
	public int addAssessmentResult(YhSleepReportApp yhSleepReport);
	/*
	 * 查询某用户所有评估记录
	 */
	public List<YhSleepReportApp> selAllReportByUser(YhSleepReportApp yhSleepReport);
	/*
	 * 查询某用户某条评估记录
	 */
	public YhSleepReportApp selOneReportByUser(YhSleepReportApp yhSleepReport);
	/*
	 * 查询评估记录条数
	 */
	public int selReportCount();
}
