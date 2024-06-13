package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhPainResolveApp;
import com.yihe.applet.api.domain.YhPainTotalReportApp;

import java.util.List;

public interface YhPainTotalReportAppService {
	 int insertSelective(YhPainTotalReportApp record);
	 YhPainTotalReportApp selectYhPainResolveLimitByParam(String userCode);
	  int YhPainTotalReportAppCount(String userCode);
	  List<YhPainResolveApp> selectAppraisalreportByParam(String userCode);
}
