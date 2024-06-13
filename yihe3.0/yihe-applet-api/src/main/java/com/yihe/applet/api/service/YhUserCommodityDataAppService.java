package com.yihe.applet.api.service;

import java.util.List;


import com.yihe.applet.api.domain.YhUserCommodityDataApp;

public interface YhUserCommodityDataAppService {
	List<YhUserCommodityDataApp> selectCommodityDataByParam(Integer groupingId,Integer variable,String userCode,Integer page,Integer pageSize);
	 YhUserCommodityDataApp selectByPrimaryKey(Integer merchandiseId);
	 int updateByPrimaryKeySelective(YhUserCommodityDataApp record);
}
