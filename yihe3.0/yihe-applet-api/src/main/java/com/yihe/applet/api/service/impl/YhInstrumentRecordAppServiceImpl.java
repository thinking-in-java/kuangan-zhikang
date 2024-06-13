package com.yihe.applet.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhInstrumentRecordApp;
import com.yihe.applet.api.mapper.YhInstrumentRecordAppMapper;
import com.yihe.applet.api.service.YhInstrumentRecordAppService;
@Service
public class YhInstrumentRecordAppServiceImpl implements YhInstrumentRecordAppService{

	@Autowired
	YhInstrumentRecordAppMapper  yhInstrumentRecordAppMapper;
	@Override
	public int insertSelective(YhInstrumentRecordApp record) {
		// TODO Auto-generated method stub
		return yhInstrumentRecordAppMapper.insertSelective(record);
	}

	@Override
	public int countData(String type) {
		// TODO Auto-generated method stub
		return yhInstrumentRecordAppMapper.countData(type);
	}

}
