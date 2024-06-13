package com.yihe.system.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserOxygen;

public interface YHUserOxygenService {
	int deleteByPrimaryKey(Integer uOxygenId);

	int insert(YHUserOxygen record);

	int insertSelective(YHUserOxygen record);

	YHUserOxygen selectByPrimaryKey(Integer uOxygenId);

	YHUserOxygen selectByPrimaryKeyByUsercodeLimit(String usercode);

	int updateByPrimaryKeySelective(YHUserOxygen record);

	int updateByPrimaryKey(YHUserOxygen record);
	
	Map<String, Object> selectYHUserOxygen(String userCode);
	
	 public PageInfo<YHUserOxygen> queryUserByCriteriaWithPage(String userCode,int page,int limit);
	   
	  PageInfo <YHUserOxygen> selectYHUserOxygen2(String userCode,int page,int limit);
	  
	  public PageInfo<YHUserOxygen> queryUserHeartByCriteriaWithPage(String userCode,int page,int limit);
	   
	  PageInfo <YHUserOxygen> selectYHUserHeart(String userCode,int page,int limit);
}
