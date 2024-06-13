package com.yihe.system.service;


import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.yihe.system.domain.YhUserCommodityData;

public interface YhUserCommodityDataService {
	public PageInfo<YhUserCommodityData> queryUserByCriteriaWithPage(Integer merchandiseType,Integer groupingId,
			Integer putaway,Integer conversion,String merchandiseName,String orgCode,int page,int limit);
	int insertSelective(YhUserCommodityData record);
	 int updateByPrimaryKeySelective(YhUserCommodityData record);
	 YhUserCommodityData selectByPrimaryKey(Integer merchandiseId);
	 int deleteByPrimaryKey(Integer merchandiseId);
	 int batchUpdateYhUserCommodityData(Map<String,Object> map);
	 int batchDeleteYhUserCommodityData(Map<String,Object> map);
	 List<YhUserCommodityData> selectAll();

}
