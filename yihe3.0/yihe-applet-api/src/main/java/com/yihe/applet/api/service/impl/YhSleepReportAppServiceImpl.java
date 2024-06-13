package com.yihe.applet.api.service.impl;

import com.yihe.applet.api.domain.YhSleepReportApp;
import com.yihe.applet.api.mapper.YhSleepReportAppMapper;
import com.yihe.applet.api.service.YhSleepReportAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @version: shayu3.0
 * @Description: 睡眠评估结果service实现层
 * @author: lhh
 * @date: 2020年4月15日 下午5:22:11
 */
@Service
@Transactional
public class YhSleepReportAppServiceImpl implements YhSleepReportAppService {
	@Autowired
	private YhSleepReportAppMapper yhSleepReportAppMapper;
	@Override
	public int addAssessmentResult(YhSleepReportApp yhSleepReport) {
		// TODO Auto-generated method stub
		return yhSleepReportAppMapper.addAssessmentResult(yhSleepReport);
	}
	@Override
	public List<YhSleepReportApp> selAllReportByUser(YhSleepReportApp yhSleepReport) {
		// TODO Auto-generated method stub
		return yhSleepReportAppMapper.selAllReportByUser(yhSleepReport);
	}
	@Override
	public YhSleepReportApp selOneReportByUser(YhSleepReportApp yhSleepReport) {
		// TODO Auto-generated method stub
		return yhSleepReportAppMapper.selOneReportByUser(yhSleepReport);
	}
	@Override
	public int selReportCount() {
		// TODO Auto-generated method stub
		return yhSleepReportAppMapper.selReportCount();
	}
}
