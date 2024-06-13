package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhSleepReportApp;

import java.util.List;

/**
 * @version: shayu3.0
 * @Description: 睡眠评估结果service
 * @author: lhh
 * @date: 2020年4月15日 下午5:06:21
 */
public interface YhSleepReportAppService {
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
