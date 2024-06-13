package com.yihe.system.service;

import java.util.Map;

import com.yihe.system.domain.YHUserHeartRate;

public interface YHUserHeartRateService {
	YHUserHeartRate selectByPrimaryKeyByUsercodeLimit(String usercode);
	
	Map<String, Object> selectHeartRate(String userCode);
	int insertSelective(YHUserHeartRate record);
}
