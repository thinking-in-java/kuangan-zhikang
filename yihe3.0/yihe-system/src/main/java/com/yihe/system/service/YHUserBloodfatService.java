package com.yihe.system.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YHUserBloodfat;

public interface YHUserBloodfatService {
	YHUserBloodfat selectByPrimaryKeyByUsercodeLimit(String usercode);
	Map<String, Object> selectYHUserBloodfat (String userCode);
	 public PageInfo<YHUserBloodfat> queryUserByCriteriaWithPage(String userCode,int page,int limit);
	int insertSelective(YHUserBloodfat record);
}
