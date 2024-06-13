package com.yihe.system.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserBloodPressure;
import com.yihe.system.domain.YHUserBloodsuger;

public interface YHUserBloodsugerService {
	YHUserBloodsuger selectByPrimaryKeyByUsercodeLimit(String usercode);
	Map<String, Object> selectYHUserBloodsuger (String userCode);
	
	 public PageInfo<YHUserBloodsuger> queryUserByCriteriaWithPage(String userCode,int page,int limit);
	   
	    PageInfo<YHUserBloodsuger> selectBloodsuger(Map<String, Object> map,int page,int limit);


	int insertSelective(YHUserBloodsuger record);
}
