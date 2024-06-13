package com.yihe.system.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserOtherfunction;

public interface YHUserOtherfunctionService {
	YHUserOtherfunction selectByPrimaryKeyByUsercodeLimit(String usercode);
	Map<String, Object> selectYHUserOtherfunction (String userCode);
	 public PageInfo<YHUserOtherfunction> queryUserByCriteriaWithPage(String userCode,int page,int limit);
	   
}
