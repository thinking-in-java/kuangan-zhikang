package com.yihe.system.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihe.system.domain.YHUserHeartRate;
import com.yihe.system.mapper.YHUserHeartRateMapper;
import com.yihe.system.service.YHUserHeartRateService;

@Service
public class YHUserHeartRateServiceImpl implements YHUserHeartRateService {
	@Autowired
	YHUserHeartRateMapper YHUserHeartRateMapper;

	@Override
	public YHUserHeartRate selectByPrimaryKeyByUsercodeLimit(String usercode) {
		// TODO Auto-generated method stub
		return YHUserHeartRateMapper.selectByPrimaryKeyByUsercodeLimit(usercode);
	}

	@Override
	public Map<String, Object> selectHeartRate(String userCode) {
		// TODO Auto-generated method stub
		return YHUserHeartRateMapper.selectHeartRate(userCode);
	}

	@Override
	public int insertSelective(YHUserHeartRate record) {
		return YHUserHeartRateMapper.insertSelective(record);
	}
}
