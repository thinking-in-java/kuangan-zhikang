package com.yihe.system.service;


import java.util.List;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhUserOrderWeb;

public interface YhUserOrderWebService {
   public PageInfo<YhUserOrderWeb> queryUserByCriteriaWithPage(String orgCode,Integer merchandiseType,Integer groupingId,
   		Integer orderStatus,String qtstrTime,String qtendTime, String merchandiseName,String name,int page, int limit);
   		int deleteByPrimaryKey(Integer orderId);
   	   public PageInfo<YhUserOrderWeb> queryUserByCriteriaWithPage2(String orgCode,Integer merchandiseType,Integer groupingId,
   	   		Integer orderStatus,String qtstrTime,String qtendTime, String merchandiseName,String name,int page, int limit);
   		int updateByPrimaryKeySelective(YhUserOrderWeb record);
   	 List<YhUserOrderWeb> selectAllYhUserOrderExport();
}
