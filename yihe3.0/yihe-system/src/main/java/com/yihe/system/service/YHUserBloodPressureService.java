package com.yihe.system.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserBloodPressure;

public interface YHUserBloodPressureService {
	int deleteByPrimaryKey(Integer uBloodpressureId);

	int insert(YHUserBloodPressure record);

	int insertSelective(YHUserBloodPressure record);

	YHUserBloodPressure selectByPrimaryKey(Integer uBloodpressureId);

	int updateByPrimaryKeySelective(YHUserBloodPressure record);

	int updateByPrimaryKey(YHUserBloodPressure record);

	String selectByPrimaryKeyByeidAndOrderby(String usercode);

	YHUserBloodPressure selectByPrimaryKeyByUsercodeLimit(String usercode);
	
	Map<String, Object> selectYHUserBloodPressure (String userCode);
	
	 public PageInfo<YHUserBloodPressure> queryUserByCriteriaWithPage(String userCode,int page,int limit);
	   
	    PageInfo<YHUserBloodPressure> selectPressure(Map<String, Object> map,int page,int limit);

}
