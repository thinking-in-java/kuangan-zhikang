package com.yihe.system.service;


import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhScoreEventCategoryWeb;


public interface YhScoreEventCategoryWebService {
	 public PageInfo<YhScoreEventCategoryWeb> queryUserByCriteriaWithPage(Integer type,Integer putaway,String eventName,int page,int limit);
	 int updateByPrimaryKeySelective(YhScoreEventCategoryWeb record);
}
