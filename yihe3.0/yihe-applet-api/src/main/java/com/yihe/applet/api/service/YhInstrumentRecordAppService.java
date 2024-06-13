package com.yihe.applet.api.service;

import com.yihe.applet.api.domain.YhInstrumentRecordApp;

public interface YhInstrumentRecordAppService {
	
	int insertSelective(YhInstrumentRecordApp record);
	
	int countData(String type);

}
