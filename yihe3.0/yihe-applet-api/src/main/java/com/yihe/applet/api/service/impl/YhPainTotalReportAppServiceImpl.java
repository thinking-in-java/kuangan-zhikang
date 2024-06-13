package com.yihe.applet.api.service.impl;

import com.yihe.applet.api.domain.YhPainResolveApp;
import com.yihe.applet.api.domain.YhPainTotalReportApp;
import com.yihe.applet.api.mapper.YhPainResolveAppMapper;
import com.yihe.applet.api.mapper.YhPainTotalReportAppMapper;
import com.yihe.applet.api.service.YhPainTotalReportAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class YhPainTotalReportAppServiceImpl implements YhPainTotalReportAppService{
	@Autowired
	YhPainTotalReportAppMapper yhPainTotalReportAppMapper;
	@Autowired
	YhPainResolveAppMapper yhPainResolveAppMapper;
	@Override
	public int insertSelective(YhPainTotalReportApp record) {
		// TODO Auto-generated method stub
		return yhPainTotalReportAppMapper.insertSelective(record);
	}
	@Override
	public YhPainTotalReportApp selectYhPainResolveLimitByParam(String userCode) {
		// TODO Auto-generated method stub
		return yhPainTotalReportAppMapper.selectYhPainResolveLimitByParam(userCode);
	}
	@Override
	public int YhPainTotalReportAppCount(String userCode) {
		// TODO Auto-generated method stub
		return yhPainTotalReportAppMapper.YhPainTotalReportAppCount(userCode);
	}
	@Override
	public List<YhPainResolveApp> selectAppraisalreportByParam(String userCode) {
		// TODO Auto-generated method stub
		return yhPainResolveAppMapper.selectAppraisalreportByParam(userCode);
	}

}
