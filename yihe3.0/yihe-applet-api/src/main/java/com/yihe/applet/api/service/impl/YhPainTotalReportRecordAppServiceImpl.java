package com.yihe.applet.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.applet.api.domain.YhPainTotalReportRecordApp;
import com.yihe.applet.api.mapper.YhPainTotalReportRecordAppMapper;
import com.yihe.applet.api.service.YhPainTotalReportRecordAppService;

@Service
public class YhPainTotalReportRecordAppServiceImpl implements YhPainTotalReportRecordAppService{
@Autowired
YhPainTotalReportRecordAppMapper yhPainTotalReportRecordAppMapper;
	@Override
	public int insertSelective(YhPainTotalReportRecordApp record) {
		// TODO Auto-generated method stub
		return yhPainTotalReportRecordAppMapper.insertSelective(record);
	}
	@Override
	public YhPainTotalReportRecordApp selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return yhPainTotalReportRecordAppMapper.selectByPrimaryKey(id);
	}

}
