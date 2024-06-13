package com.yihe.system.service;

import org.apache.ibatis.annotations.Param;

import com.yihe.system.domain.YhUserStepWeb;

public interface YhUserStepWebService {  
	 int deleteByPrimaryKey(Integer stepId);
	 int insertSelective(YhUserStepWeb record);
	  YhUserStepWeb selectByPrimaryKey(Integer stepId);
	    int updateByPrimaryKeySelective(YhUserStepWeb record);
	    YhUserStepWeb selectUserIntegral(@Param("userCode") String userCode);
}
