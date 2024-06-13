package com.yihe.system.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserUba;

public interface YHUserUbaService {
	YHUserUba selectByPrimaryKeyByUsercodeLimit(String usercode);
	Map<String, Object> selectYHUserUba(String userCode);
	 public PageInfo<YHUserUba> queryUserByCriteriaWithPage(String userCode,int page,int limit);
}
