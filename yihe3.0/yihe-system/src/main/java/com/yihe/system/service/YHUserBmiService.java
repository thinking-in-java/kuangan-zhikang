package com.yihe.system.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserBmi;

public interface YHUserBmiService {

	int saveBmi(YHUserBmi bmi);
	YHUserBmi selectByPrimaryKeyByUsercodeLimit(String usercode);
	Map<String, Object> selectYHUserBmi (String userCode);
	
	 public PageInfo<YHUserBmi> queryUserByCriteriaWithPage(String userCode,int page,int limit);
	   
	    PageInfo<YHUserBmi> selectBmiPage(String userCode,int page,int limit);
}
