package com.yihe.system.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserLiverfunction;

public interface YHUserLiverfunctionService {
	YHUserLiverfunction selectByPrimaryKeyByUsercodeLimit(String usercode);
	
	Map<String, Object> selectYHUserLiverfunction (String userCode);
	 public PageInfo<YHUserLiverfunction> queryUserByCriteriaWithPage(String userCode,int page,int limit);
	
}
