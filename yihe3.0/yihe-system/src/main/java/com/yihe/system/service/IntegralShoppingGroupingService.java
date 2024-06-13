package com.yihe.system.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.IntegralShoppingGrouping;

public interface IntegralShoppingGroupingService {
	public PageInfo<IntegralShoppingGrouping> queryGroupingByCriteriaWithPage(String name,String orgCode,int page,int limit);
	 int deleteByPrimaryKey(Integer groupingId);
	 int updateByPrimaryKeySelective(IntegralShoppingGrouping record);
	 int insertSelective(IntegralShoppingGrouping record);
	 IntegralShoppingGrouping selectGroupingName(String name);
	 List<IntegralShoppingGrouping> selectAllGrouping(String orgCode);
	 List<IntegralShoppingGrouping> selectGrouping(String name);
}
