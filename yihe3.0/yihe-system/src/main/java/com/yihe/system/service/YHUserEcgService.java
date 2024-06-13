package com.yihe.system.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserEcg;

public interface YHUserEcgService {
	YHUserEcg selectByPrimaryKeyByUsercodeLimit(String usercode);
	Map<String, Object> selectYHUserEcg (String userCode);
	 public PageInfo<YHUserEcg> queryUserByCriteriaWithPage(String userCode,int page,int limit);
	   
}
