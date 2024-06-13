package com.yihe.system.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserBodyFat;

public interface YHUserBodyFatService {
	YHUserBodyFat selectByPrimaryKeyByUsercodeLimit(String usercode);
	
	Map<String, Object> selectYHUserBodyFat (String userCode);
	
	public PageInfo<YHUserBodyFat> queryUserByCriteriaWithPage(String userCode,int page,int limit);
	
	public PageInfo<YHUserBodyFat> queryUserByCriteriaWithPage1(String userCode,int page,int limit);
	   
    PageInfo<YHUserBodyFat> selectBodyFat(String userCode,int page,int limit);

	int insertSelective(YHUserBodyFat record);
}
