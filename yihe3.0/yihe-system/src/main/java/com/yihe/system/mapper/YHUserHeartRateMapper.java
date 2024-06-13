package com.yihe.system.mapper;

import java.util.Map;

import com.yihe.system.domain.YHUserHeartRate;

public interface YHUserHeartRateMapper {
	int deleteByPrimaryKey(Integer rid);

	int insert(YHUserHeartRate record);

	int insertSelective(YHUserHeartRate record);

	YHUserHeartRate selectByPrimaryKey(Integer rid);

	YHUserHeartRate selectByPrimaryKeyByUsercodeLimit(String usercode);

	int updateByPrimaryKeySelective(YHUserHeartRate record);

	int updateByPrimaryKey(YHUserHeartRate record);
	
	Map<String, Object> selectHeartRate(String userCode);
}