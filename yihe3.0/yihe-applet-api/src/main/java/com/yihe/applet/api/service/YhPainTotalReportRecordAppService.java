package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhPainTotalReportRecordApp;

public interface YhPainTotalReportRecordAppService {
	int insertSelective(YhPainTotalReportRecordApp record);
	YhPainTotalReportRecordApp selectByPrimaryKey(Integer id);

}
