package com.yihe.system.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserRenalfunction;

public interface YHUserRenalfunctionService {
	YHUserRenalfunction selectByPrimaryKeyByUsercodeLimit(String usercode);
	
	Map<String, Object> selectYHUserRenalfunction (String userCode);
	 public PageInfo<YHUserRenalfunction> queryUserByCriteriaWithPage(String userCode,int page,int limit);
	int insertSelective(YHUserRenalfunction record);
}
